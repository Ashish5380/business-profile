package com.intuit.businessprofile.service.impl;

import com.intuit.businessprofile.bo.BusinessProfileRevisionBO;
import com.intuit.businessprofile.constant.StringConstants;
import com.intuit.businessprofile.entity.BusinessProfileRevisionEntity;
import com.intuit.businessprofile.exception.InvalidBusinessProfileRevisionException;
import com.intuit.businessprofile.mapper.BusinessProfileRevisionMapper;
import com.intuit.businessprofile.model.BusinessProfileModel;
import com.intuit.businessprofile.repository.BusinessProfileRevisionDataAccess;
import com.intuit.businessprofile.service.BusinessProfileRevision;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class BusinessProfileRevisionImpl implements BusinessProfileRevision {
    private final BusinessProfileRevisionDataAccess businessProfileRevisionDataAccess;

    /**
     * Retrieves the Business Profile revision information for a specific Business Profile ID and revision number.
     *
     * @param businessProfileId The unique identifier of the Business Profile.
     * @param revision          The revision number for which the Business Profile information is to be retrieved.
     * @return The {@link BusinessProfileRevisionBO} representing the Business Profile revision information.
     * @throws InvalidBusinessProfileRevisionException If the specified Business Profile revision does not exist.
     *                                                 This exception is thrown when there is no matching Business Profile for the provided Business Profile ID
     *                                                 and revision number.
     */
    @Override
    public BusinessProfileRevisionBO getBusinessProfileRevision(String businessProfileId, Integer revision) {

        // Get Business profile of a specific revision
        List<BusinessProfileRevisionEntity> businessProfileRevision = businessProfileRevisionDataAccess.getBusinessProfileForRevision(businessProfileId, revision);
        log.info("Business-profile revision for businessProfileId :: {}, revision :: {}", businessProfileId, businessProfileRevision);

        // Get First element from the result set and map to bo then
        return Optional.ofNullable(CollectionUtils.firstElement(businessProfileRevision)).map(BusinessProfileRevisionMapper::mapToBO).orElseThrow(() -> new InvalidBusinessProfileRevisionException(StringConstants.Error.INVALID_BUSINESS_PROFILE_REVISION, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Creates a new Business Profile revision based on the provided BusinessProfileRevisionBO (Business Object).
     *
     * @param internalRequest The {@link BusinessProfileRevisionBO} representing the information of the new Business Profile revision.
     * @return The {@link BusinessProfileModel} representing the newly created Business Profile revision.
     */
    @Override
    public BusinessProfileModel createBusinessProfileRevision(BusinessProfileRevisionBO internalRequest) {
        return businessProfileRevisionDataAccess.save(BusinessProfileRevisionMapper.map(internalRequest));
    }

    @Override
    public BusinessProfileRevisionBO getLatestBusinessProfileRevision(String businessProfileId){
        // Get Business profile of latest revision
        List<BusinessProfileRevisionEntity> businessProfileRevision = businessProfileRevisionDataAccess
          .getLatestBusinessProfileRevision(businessProfileId);
        // Get First element from the result set and map to bo then
        return Optional.ofNullable(CollectionUtils.firstElement(businessProfileRevision)).map(BusinessProfileRevisionMapper::mapToBO).orElseThrow(() -> new InvalidBusinessProfileRevisionException(StringConstants.Error.INVALID_BUSINESS_PROFILE_REVISION, HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
