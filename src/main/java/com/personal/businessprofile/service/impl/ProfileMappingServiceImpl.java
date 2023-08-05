package com.personal.businessprofile.service.impl;

import com.personal.businessprofile.bo.ProfileMappingBO;
import com.personal.businessprofile.constant.StringConstants;
import com.personal.businessprofile.entity.ProfileMappingEntity;
import com.personal.businessprofile.enums.ValidationStatus;
import com.personal.businessprofile.exception.InvalidValidationStateException;
import com.personal.businessprofile.mapper.ProfileMappingMapper;
import com.personal.businessprofile.repository.ProfileMappingDataAccess;
import com.personal.businessprofile.service.ProfileMappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProfileMappingServiceImpl implements ProfileMappingService {

    private final ProfileMappingDataAccess profileMappingDataAccess;

    @Override
    public void createProfileMapping(ProfileMappingBO profileMappingBO, ValidationStatus validationStatus) {
        profileMappingBO.setValidationStatus(validationStatus);
        log.info("Creating profile mapping for request :: {}, with validation status :: {}",
          profileMappingBO, validationStatus);
        profileMappingDataAccess.save(ProfileMappingMapper.map(profileMappingBO));
    }

    @Override
    public void updateProfileMapping(ProfileMappingBO profileMappingBO, ValidationStatus validationStatus) {
        profileMappingBO.setValidationStatus(validationStatus);
        log.info("Creating profile mapping for request :: {}, with validation status :: {}",
          profileMappingBO, validationStatus);
        profileMappingDataAccess.upsertProfileMapping(ProfileMappingMapper.map(profileMappingBO));
    }

    @Override
    public ProfileMappingBO getLatestProfileMapping(String businessProfileId, String externalProductId) {
        List<ProfileMappingEntity> profileMappingList = profileMappingDataAccess
          .findLatestMapping(
            businessProfileId,
            externalProductId);
        log.info("Fetched latest profile mapping for business-profile :: {}, " +
          "externalProductId :: {}, latest entry :: {}", businessProfileId, externalProductId, profileMappingList);
        return Optional.ofNullable(CollectionUtils.firstElement(profileMappingList))
          .map(ProfileMappingMapper::mapToBO)
          .orElseThrow(() -> new InvalidValidationStateException(StringConstants.Error.INVALID_VALIDATION_STATE,
            HttpStatus.BAD_REQUEST));
    }

    @Override
    public List<ProfileMappingBO> getAllLatestValidMappingForBusiness(String businessProfileId) {
        List<ProfileMappingEntity> mappingEntities = profileMappingDataAccess
          .findDocumentWithLatestValidRevision(businessProfileId);

        log.info("Latest valid profile-mapping for business-profile :: {}, mappings :: {}", businessProfileId,
          mappingEntities);
        return mappingEntities.stream()
          .map(ProfileMappingMapper::mapToBO)
          .collect(Collectors.toList());
    }

    @Override
    public ProfileMappingBO getLatestValidMappingForBusinessAndProduct(String businessProfileId,
                                                                       String externalProductId) {
        List<ProfileMappingEntity> profileMappingList = profileMappingDataAccess
          .findLatestValidMapping(businessProfileId, externalProductId);
        log.info("Latest valid profile mapping for business-profile :: {}, externalProductId :: {}, mapping :: {}",
          businessProfileId, externalProductId, profileMappingList);
        return Optional.ofNullable(CollectionUtils.firstElement(profileMappingList))
          .map(ProfileMappingMapper::mapToBO)
          .filter(mapping -> mapping.getValidationStatus().equals(ValidationStatus.VALID))
          .orElseThrow(() -> new InvalidValidationStateException(StringConstants.Error.INVALID_VALIDATION_STATE,
            HttpStatus.BAD_REQUEST));
    }
}
