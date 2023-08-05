package com.personal.businessprofile.repository;

import com.personal.businessprofile.entity.ProfileMappingEntity;

import java.util.List;

public interface ProfileMappingDataAccess {

    List<ProfileMappingEntity> findLatestMapping(String businessProfileId, String externalProductId);

    ProfileMappingEntity save(ProfileMappingEntity profileMapping);

    List<ProfileMappingEntity> findDocumentWithLatestValidRevision(String businessProfileId);

    List<ProfileMappingEntity> findLatestValidMapping(String businessProfileId, String externalProductId);

    void upsertProfileMapping(ProfileMappingEntity entity);
}
