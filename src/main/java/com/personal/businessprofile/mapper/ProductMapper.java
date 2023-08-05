package com.personal.businessprofile.mapper;

import com.personal.businessprofile.bo.ProductBO;
import com.personal.businessprofile.dto.request.ProductRequest;
import com.personal.businessprofile.dto.response.ProductResponse;
import com.personal.businessprofile.entity.ProductEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {

    public ProductBO mapToResponse(ProductEntity entity) {
        return ProductBO.builder()
          .id(entity.getId())
          .externalProductId(entity.getExternalProductId())
          .name(entity.getName())
          .description(entity.getDescription())
          .build();
    }

    public ProductEntity map(ProductRequest request) {
        return ProductEntity.builder()
          .externalProductId(request.getExternalProductId())
          .description(request.getDescription())
          .name(request.getName())
          .build();
    }

    public ProductResponse mapToResponse(ProductBO bo){
        return ProductResponse.builder()
          .description(bo.getDescription())
          .name(bo.getName())
          .externalProductId(bo.getExternalProductId())
          .build();
    }

}
