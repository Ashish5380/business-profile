package com.personal.businessprofile.mapper;

import com.personal.businessprofile.bo.BusinessProfileResponseBO;
import com.personal.businessprofile.bo.BusinessProfileRevisionBO;
import com.personal.businessprofile.dto.request.BusinessProfileUpdateRequest;
import com.personal.businessprofile.entity.BusinessProfileRevisionEntity;
import lombok.experimental.UtilityClass;
import org.bson.types.ObjectId;
import org.springframework.util.ObjectUtils;

@UtilityClass
public class BusinessProfileRevisionMapper {

    public BusinessProfileRevisionEntity map(BusinessProfileRevisionBO request) {
        return BusinessProfileRevisionEntity.builder()
          .companyName(request.getCompanyName())
          .legalName(request.getLegalName())
          .legalAddress(request.getLegalAddress())
          .businessAddress(request.getBusinessAddress())
          .email(request.getEmail())
          .website(request.getWebsite())
          .taxIdentifier(request.getTaxIdentifier())
          .revision(request.getRevision())
          .businessProfileId(request.getBusinessProfileId())
          .build();
    }

    public BusinessProfileRevisionBO mapRequestToBO(BusinessProfileUpdateRequest request,
                                                    BusinessProfileResponseBO validProfile){
        return BusinessProfileRevisionBO.builder()
          .legalName(ObjectUtils.isEmpty(request.getLegalName()) ? validProfile.getLegalName() : request.getLegalName())
          .taxIdentifier(ObjectUtils.isEmpty(
            request.getTaxIdentifier()) ? validProfile.getTaxIdentifier() : request.getTaxIdentifier())
          .companyName(
            ObjectUtils.isEmpty(request.getCompanyName()) ? validProfile.getCompanyName() : request.getCompanyName())
          .website(ObjectUtils.isEmpty(request.getWebsite()) ? validProfile.getWebsite() : request.getWebsite())
          .businessAddress(ObjectUtils.isEmpty(request.getBusinessAddress()) ?
            validProfile.getBusinessAddress() : request.getBusinessAddress())
          .legalAddress(ObjectUtils.isEmpty(request.getLegalAddress()) ?
            validProfile.getLegalAddress() : request.getLegalAddress())
          .email(ObjectUtils.isEmpty(request.getEmail()) ? validProfile.getEmail() : request.getEmail())
          .businessProfileId(validProfile.getId().toString())
          .build();
    }

    public BusinessProfileRevisionBO mapBusinessProfileBOToRevisionBO(BusinessProfileResponseBO request) {
        return BusinessProfileRevisionBO.builder()
          .businessAddress(request.getBusinessAddress())
          .taxIdentifier(request.getTaxIdentifier())
          .website(request.getWebsite())
          .legalName(request.getLegalName())
          .companyName(request.getCompanyName())
          .legalAddress(request.getLegalAddress())
          .email(request.getEmail())
          .businessProfileId(request.getId().toString())
          .build();
    }

    public BusinessProfileRevisionBO mapToBO(BusinessProfileRevisionEntity entity){
        return BusinessProfileRevisionBO.builder()
          .businessProfileId(entity.getBusinessProfileId())
          .revision(entity.getRevision())
          .businessAddress(entity.getBusinessAddress())
          .legalAddress(entity.getLegalAddress())
          .companyName(entity.getCompanyName())
          .website(entity.getWebsite())
          .email(entity.getEmail())
          .legalName(entity.getLegalName())
          .taxIdentifier(entity.getTaxIdentifier())
          .build();
    }

    public BusinessProfileResponseBO mapRevisionToResponse(BusinessProfileRevisionBO revisionBO){
        return BusinessProfileResponseBO.builder()
          .latestValidRevision(revisionBO.getRevision())
          .website(revisionBO.getWebsite())
          .email(revisionBO.getEmail())
          .businessAddress(revisionBO.getBusinessAddress())
          .legalAddress(revisionBO.getLegalAddress())
          .legalName(revisionBO.getLegalName())
          .companyName(revisionBO.getCompanyName())
          .taxIdentifier(revisionBO.getTaxIdentifier())
          .id(new ObjectId(revisionBO.getBusinessProfileId()))
          .build();
    }
}
