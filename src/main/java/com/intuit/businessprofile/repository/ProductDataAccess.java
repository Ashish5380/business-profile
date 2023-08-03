package com.intuit.businessprofile.repository;

import com.intuit.businessprofile.entity.ProductEntity;

import java.util.List;

public interface ProductDataAccess {

    List<ProductEntity> getProductByExternalId(String externalProductId);

    ProductEntity save(ProductEntity product);
}
