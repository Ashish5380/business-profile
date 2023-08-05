package com.personal.businessprofile.repository.mongo;

import com.personal.businessprofile.entity.ProfileMappingEntity;
import com.personal.businessprofile.model.ProfileMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProfileMappingMongoRepository extends MongoRepository<ProfileMappingEntity, String> {

    List<ProfileMapping> findByBusinessProfileIdAndExternalProductId(String businessProfileId,
                                                                     String externalProductId);
}
