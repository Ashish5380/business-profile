package com.intuit.businessprofile.repository.mongo;

import com.intuit.businessprofile.entity.ProfileMappingEntity;
import com.intuit.businessprofile.model.ProfileMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProfileMappingMongoRepository extends MongoRepository<ProfileMappingEntity, String> {

    List<ProfileMapping> findByBusinessProfileIdAndExternalProductId(String businessProfileId,
                                                                     String externalProductId);
}
