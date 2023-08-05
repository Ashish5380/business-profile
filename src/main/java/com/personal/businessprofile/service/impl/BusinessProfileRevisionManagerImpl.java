package com.personal.businessprofile.service.impl;

import com.personal.businessprofile.bo.BusinessProfileResponseBO;
import com.personal.businessprofile.bo.BusinessProfileRevisionBO;
import com.personal.businessprofile.bo.ProfileMappingBO;
import com.personal.businessprofile.cache.Locker;
import com.personal.businessprofile.constant.KeyConstants;
import com.personal.businessprofile.constant.StringConstants;
import com.personal.businessprofile.dto.request.BusinessProfileCreateRequest;
import com.personal.businessprofile.dto.request.BusinessProfileUpdateRequest;
import com.personal.businessprofile.enums.ValidationStatus;
import com.personal.businessprofile.exception.ResourceOccupiedException;
import com.personal.businessprofile.mapper.BusinessProfileRevisionMapper;
import com.personal.businessprofile.mapper.ProfileMappingMapper;
import com.personal.businessprofile.model.BusinessProfileModel;
import com.personal.businessprofile.service.BusinessProfileRevision;
import com.personal.businessprofile.service.BusinessProfileRevisionManager;
import com.personal.businessprofile.service.BusinessProfileService;
import com.personal.businessprofile.service.ProfileMappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
@RequiredArgsConstructor
public class BusinessProfileRevisionManagerImpl implements BusinessProfileRevisionManager {

    private final BusinessProfileRevision businessProfileRevision;

    private final BusinessProfileService businessProfileService;

    private final RevisionObserverRegistry observerRegistry;

    private final ProfileMappingService profileMappingService;

    private final Locker locker;

    public BusinessProfileResponseBO getBusinessProfile(String businessProfileId) {
        return businessProfileService.fetchBusinessProfile(businessProfileId);
    }

    public List<BusinessProfileResponseBO> getAllBusinessProfile() {
        return businessProfileService.getAllBusinessProfile();
    }

    @Transactional
    public BusinessProfileResponseBO createBusinessProfile(final BusinessProfileCreateRequest request) {
        // Implemented lock using distributed caching, for single instance java.util.concurrent LOCK INTERFACE can
        // be used as well.
        if(locker.hasLock(
          String.format(KeyConstants.Keys.BUSINESS_PROFILE_CREATE_KEY,request.getEmail()),
          10,
          TimeUnit.MILLISECONDS)) {
            log.info("Creating business profile for creation request :: {}", request);
            BusinessProfileResponseBO response = businessProfileService.createBusinessProfile(request);
            updateRevision(response);
            locker.releaseLock(String.format(KeyConstants.Keys.BUSINESS_PROFILE_CREATE_KEY,request.getEmail()));
            return response;
        }else{
            log.error("Cannot perform create operation for business-profile with email :: {}, " +
              "as creation request for same profile is currently ongoing.", request.getEmail());
            throw new ResourceOccupiedException(StringConstants.Error.RESOURCE_OCCUPIED, HttpStatus.CONFLICT);
        }
    }

    public BusinessProfileModel updateBusinessProfileRevisionForProduct(String externalProductId,
                                                                        String businessProfileId,
                                                                        BusinessProfileUpdateRequest request) {
        // Implemented lock using distributed caching, for single instance java.util.concurrent LOCK INTERFACE can
        // be used as well.
        if(locker.hasLock(
          String.format(KeyConstants.Keys.BUSINESS_PROFILE_REVISION_KEY,businessProfileId),
          10,
          TimeUnit.MILLISECONDS)) {
            BusinessProfileResponseBO validProfile = getBusinessProfile(businessProfileId);
            BusinessProfileRevisionBO latestProfileRevision = getLatestProfileRevision(externalProductId,
              businessProfileId);
            BusinessProfileRevisionBO revisionToUpdate = BusinessProfileRevisionMapper.mapRequestToBO(request,
              validProfile);
            revisionToUpdate.setRevision(latestProfileRevision.getRevision() + 1);
            log.info("Revision to update for business-profile :: {}, for externalProductId :: {}, revision :: {}",
              businessProfileId, externalProductId, revisionToUpdate);

            BusinessProfileModel updatedBusinessProfile = businessProfileRevision
              .createBusinessProfileRevision(revisionToUpdate);
            profileMappingService.createProfileMapping(ProfileMappingMapper.mapToBO(
              revisionToUpdate.getBusinessProfileId(),
              externalProductId,
              revisionToUpdate.getRevision()), ValidationStatus.INITIATED);
            observerRegistry.notifyObservers(businessProfileId, revisionToUpdate);
            locker.releaseLock(String.format(KeyConstants.Keys.BUSINESS_PROFILE_REVISION_KEY,businessProfileId));
            return updatedBusinessProfile;
        }else{
            log.error("Cannot perform update operation on business-profile :: {} as resource is occupied for " +
              "another update", businessProfileId);
            throw new ResourceOccupiedException(StringConstants.Error.RESOURCE_OCCUPIED, HttpStatus.CONFLICT);
        }
    }

    public void updateBusinessProfile(BusinessProfileRevisionBO revision) {
        businessProfileService.updateBusinessProfileForRevision(revision);
    }

    public BusinessProfileRevisionBO fetchLatestBusinessProfileForProduct(final String businessProfileId,
                                                                          final String externalProductId) {
        ProfileMappingBO profileMapping = profileMappingService
          .getLatestValidMappingForBusinessAndProduct(businessProfileId, externalProductId);
        log.info("Fetching latest profile mapping for businessProfileId :: {}, externalProductId :: {}, " +
          "for revision :: {}", businessProfileId, externalProductId, profileMapping.getRevision());
        return fetchBusinessProfileRevision(businessProfileId, profileMapping.getRevision());

    }

    public BusinessProfileRevisionBO fetchBusinessProfileRevision(String businessProfileId, Integer revision) {
        return businessProfileRevision.getBusinessProfileRevision(businessProfileId, revision);
    }

    private BusinessProfileModel updateRevision(BusinessProfileResponseBO savedProfileResponse) {
        BusinessProfileRevisionBO revisionBO =
          BusinessProfileRevisionMapper.mapBusinessProfileBOToRevisionBO(savedProfileResponse);
        revisionBO.setRevision(0);
        log.info("Update revision for business-profile :: {}, revision :: {}",
          savedProfileResponse.getId().toString(), revisionBO);
        return businessProfileRevision.createBusinessProfileRevision(revisionBO);
    }

    private BusinessProfileRevisionBO getLatestProfileRevision(String externalProductId,
                                                              String businessProfileId) {

        ProfileMappingBO profileMapping = fetchLatestProfileMapping(businessProfileId,
          externalProductId);
        return fetchBusinessProfileRevision(businessProfileId, profileMapping.getRevision());

    }

    private ProfileMappingBO fetchLatestProfileMapping(String businessProfileId, String externalProductId) {
        return profileMappingService.getLatestProfileMapping(businessProfileId, externalProductId);
    }

    @Override
    public BusinessProfileModel updateBusinessProfileRevision(String businessProfileId,
                                                              BusinessProfileUpdateRequest request) {
        // Implemented lock using distributed caching, for single instance java.util.concurrent LOCK INTERFACE can
        // be used as well.
        if(locker.hasLock(
          String.format(KeyConstants.Keys.BUSINESS_PROFILE_REVISION_KEY,businessProfileId),
          1,
          TimeUnit.MINUTES)) {
            BusinessProfileResponseBO validProfile = getBusinessProfile(businessProfileId);
            BusinessProfileRevisionBO latestProfileRevision = businessProfileRevision
              .getLatestBusinessProfileRevision(businessProfileId);
            BusinessProfileRevisionBO revisionToUpdate = BusinessProfileRevisionMapper.mapRequestToBO(request,
              validProfile);
            revisionToUpdate.setRevision(latestProfileRevision.getRevision() + 1);
            log.info("Revision to update for business-profile :: {}, revision :: {}",
              businessProfileId, revisionToUpdate);

            BusinessProfileModel updatedBusinessProfile = businessProfileRevision
              .createBusinessProfileRevision(revisionToUpdate);
            observerRegistry.notifyObservers(businessProfileId, revisionToUpdate);
            locker.releaseLock(String.format(KeyConstants.Keys.BUSINESS_PROFILE_REVISION_KEY,businessProfileId));
            return updatedBusinessProfile;
        }else{
            log.error("Cannot perform update operation on business-profile :: {} as resource is occupied for " +
              "another update", businessProfileId);
            throw new ResourceOccupiedException(StringConstants.Error.RESOURCE_OCCUPIED, HttpStatus.CONFLICT);
        }
    }

}
