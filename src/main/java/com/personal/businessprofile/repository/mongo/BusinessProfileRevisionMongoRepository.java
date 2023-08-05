package com.personal.businessprofile.repository.mongo;

import com.personal.businessprofile.entity.BusinessProfileRevisionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BusinessProfileRevisionMongoRepository extends MongoRepository<BusinessProfileRevisionEntity, String> {

    List<BusinessProfileRevisionEntity> getByRevisionAndBusinessProfileId(Integer revision, String businessProfileId);
}
