package com.intuit.businessprofile.mapper;

import com.intuit.businessprofile.bo.BusinessProfileResponseBO;
import com.intuit.businessprofile.bo.ProductBO;
import com.intuit.businessprofile.bo.ProfileMappingBO;
import com.intuit.businessprofile.entity.ProfileMappingEntity;
import com.intuit.businessprofile.enums.ValidationStatus;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProfileMappingMapper {

    public ProfileMappingEntity map(ProfileMappingBO bo) {
        return ProfileMappingEntity.builder()
          .businessProfileId(bo.getBusinessProfileId())
          .externalProductId(bo.getExternalProductId())
          .revision(bo.getRevision())
          .validationStatus(bo.getValidationStatus())
          .build();
    }

    public ProfileMappingBO mapToBO(String businessProfileId, String externalProductId, Integer revision){
        return ProfileMappingBO.builder()
          .businessProfileId(businessProfileId)
          .externalProductId(externalProductId)
          .revision(revision)
          .build();
    }

    public ProfileMappingBO mapToBO(ProfileMappingEntity entity){
        return ProfileMappingBO.builder()
          .revision(entity.getRevision())
          .businessProfileId(entity.getBusinessProfileId())
          .externalProductId(entity.getExternalProductId())
          .validationStatus(entity.getValidationStatus())
          .validationError(entity.getValidationError())
          .build();
    }
}
