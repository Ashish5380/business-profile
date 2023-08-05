package com.personal.businessprofile.repository.mongo;

import com.personal.businessprofile.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductMongoRepository extends MongoRepository<ProductEntity, String> {

    List<ProductEntity> getByExternalProductId(String externalProductId);
}
