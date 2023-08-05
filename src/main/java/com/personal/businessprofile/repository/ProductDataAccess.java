package com.personal.businessprofile.repository;

import com.personal.businessprofile.entity.ProductEntity;

import java.util.List;

public interface ProductDataAccess {

    List<ProductEntity> getProductByExternalId(String externalProductId);

    ProductEntity save(ProductEntity product);
}
