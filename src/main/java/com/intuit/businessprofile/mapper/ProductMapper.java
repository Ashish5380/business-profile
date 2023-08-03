package com.intuit.businessprofile.mapper;

import com.intuit.businessprofile.bo.ProductBO;
import com.intuit.businessprofile.dto.request.ProductRequest;
import com.intuit.businessprofile.dto.response.ProductResponse;
import com.intuit.businessprofile.entity.ProductEntity;
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
