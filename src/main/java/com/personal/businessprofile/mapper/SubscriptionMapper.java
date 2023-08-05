package com.personal.businessprofile.mapper;

import com.personal.businessprofile.bo.BusinessProfileResponseBO;
import com.personal.businessprofile.bo.ProductBO;
import com.personal.businessprofile.bo.SubscriptionBO;
import com.personal.businessprofile.dto.response.SubscriptionResponse;
import com.personal.businessprofile.entity.SubscriptionEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SubscriptionMapper {

    public SubscriptionEntity map(ProductBO productResponse, BusinessProfileResponseBO profile) {
        return SubscriptionEntity.builder()
          .businessProfileId(profile.getId().toString())
          .externalProductId(productResponse.getExternalProductId())
          .isActive(true)
          .build();
    }

    public SubscriptionBO mapToResponse(SubscriptionEntity entity) {
        return SubscriptionBO.builder()
          .businessProfileId(entity.getBusinessProfileId())
          .externalProductId(entity.getExternalProductId())
          .isActive(entity.getIsActive())
          .build();
    }

    public SubscriptionEntity mapResponseToEntity(SubscriptionBO bo) {
        return SubscriptionEntity.builder()
          .businessProfileId(bo.getBusinessProfileId())
          .externalProductId(bo.getExternalProductId())
          .isActive(bo.getIsActive())
          .id(bo.getId())
          .build();
    }

    public SubscriptionResponse mapToResponse(SubscriptionBO bo){
        return SubscriptionResponse.builder()
          .isActive(bo.getIsActive())
          .businessProfileId(bo.getBusinessProfileId())
          .externalProductId(bo.getExternalProductId())
          .build();
    }
}
