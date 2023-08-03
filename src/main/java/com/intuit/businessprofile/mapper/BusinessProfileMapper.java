package com.intuit.businessprofile.mapper;

import com.intuit.businessprofile.bo.BusinessProfileResponseBO;
import com.intuit.businessprofile.bo.BusinessProfileRevisionBO;
import com.intuit.businessprofile.dto.request.BusinessProfileCreateRequest;
import com.intuit.businessprofile.dto.response.BusinessProfileResponse;
import com.intuit.businessprofile.entity.BusinessProfileEntity;
import com.intuit.businessprofile.model.BusinessProfileModel;
import lombok.experimental.UtilityClass;
import org.bson.types.ObjectId;

@UtilityClass
public class BusinessProfileMapper {

    public BusinessProfileEntity map(BusinessProfileCreateRequest request) {
        return BusinessProfileEntity.builder()
          .companyName(request.getCompanyName())
          .legalName(request.getLegalName())
          .legalAddress(request.getLegalAddress())
          .businessAddress(request.getBusinessAddress())
          .email(request.getEmail())
          .website(request.getWebsite())
          .taxIdentifier(request.getTaxIdentifier())
          .latestValidRevision(0)
          .build();
    }

    public BusinessProfileResponseBO mapToBO(BusinessProfileEntity entity) {
        return BusinessProfileResponseBO.builder()
          .id(entity.getId())
          .businessAddress(entity.getBusinessAddress())
          .legalAddress(entity.getLegalAddress())
          .email(entity.getEmail())
          .website(entity.getWebsite())
          .companyName(entity.getCompanyName())
          .legalName(entity.getLegalName())
          .taxIdentifier(entity.getTaxIdentifier())
          .latestValidRevision(entity.getLatestValidRevision())
          .build();
    }

    public BusinessProfileResponse mapToResponse(BusinessProfileResponseBO bo){
        return BusinessProfileResponse.builder()
          .id(bo.getId().toString())
          .businessAddress(bo.getBusinessAddress())
          .legalAddress(bo.getLegalAddress())
          .email(bo.getEmail())
          .website(bo.getWebsite())
          .companyName(bo.getCompanyName())
          .legalName(bo.getLegalName())
          .taxIdentifier(bo.getTaxIdentifier())
          .latestValidRevision(bo.getLatestValidRevision())
          .build();
    }

    public BusinessProfileEntity mapRevisionToEntity(BusinessProfileRevisionBO revisionBO){
        return BusinessProfileEntity.builder()
          .latestValidRevision(revisionBO.getRevision())
          .email(revisionBO.getEmail())
          .website(revisionBO.getWebsite())
          .legalAddress(revisionBO.getLegalAddress())
          .businessAddress(revisionBO.getBusinessAddress())
          .legalName(revisionBO.getLegalName())
          .companyName(revisionBO.getCompanyName())
          .taxIdentifier(revisionBO.getTaxIdentifier())
          .id(new ObjectId(revisionBO.getBusinessProfileId()))
          .build();
    }

    public BusinessProfileResponse mapModelToResponse(BusinessProfileModel model){
        return new BusinessProfileResponse().toBuilder()
          .id(model.getId().toString())
          .businessAddress(model.getBusinessAddress())
          .legalAddress(model.getLegalAddress())
          .email(model.getEmail())
          .website(model.getWebsite())
          .companyName(model.getCompanyName())
          .legalName(model.getLegalName())
          .taxIdentifier(model.getTaxIdentifier())
          .build();
    }
}
