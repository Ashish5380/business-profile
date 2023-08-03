package com.intuit.businessprofile.repository.impl;

import com.intuit.businessprofile.entity.ProductEntity;
import com.intuit.businessprofile.repository.ProductDataAccess;
import com.intuit.businessprofile.repository.mongo.ProductMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductDataAccessImpl implements ProductDataAccess {
    private final MongoTemplate template;

    private final ProductMongoRepository productMongoRepository;

    @Override
    public List<ProductEntity> getProductByExternalId(String externalProductId) {
        return productMongoRepository.getByExternalProductId(externalProductId);
    }


    @Override
    public ProductEntity save(ProductEntity product) {
        return productMongoRepository.save(product);
    }
}
