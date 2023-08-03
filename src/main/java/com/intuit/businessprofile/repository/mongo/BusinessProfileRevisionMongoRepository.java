package com.intuit.businessprofile.repository.mongo;

import com.intuit.businessprofile.entity.BusinessProfileRevisionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BusinessProfileRevisionMongoRepository extends MongoRepository<BusinessProfileRevisionEntity, String> {

    List<BusinessProfileRevisionEntity> getByRevisionAndBusinessProfileId(Integer revision, String businessProfileId);
}
