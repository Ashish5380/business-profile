package com.intuit.businessprofile.repository.mongo;

import com.intuit.businessprofile.entity.BusinessProfileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BusinessProfileMongoRepository extends MongoRepository<BusinessProfileEntity, String> {

    List<BusinessProfileEntity> getByEmail(String email);
}
