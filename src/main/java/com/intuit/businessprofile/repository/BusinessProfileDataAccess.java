package com.intuit.businessprofile.repository;

import com.intuit.businessprofile.entity.BusinessProfileEntity;

import java.util.List;
import java.util.Optional;

public interface BusinessProfileDataAccess {

    List<BusinessProfileEntity> getBusinessProfileByEmail(final String email);

    void upsertBusinessProfile(BusinessProfileEntity entity);

    BusinessProfileEntity save(BusinessProfileEntity profile);

    Optional<BusinessProfileEntity> findById(String businessProfileId);

    List<BusinessProfileEntity> findAll();


}
