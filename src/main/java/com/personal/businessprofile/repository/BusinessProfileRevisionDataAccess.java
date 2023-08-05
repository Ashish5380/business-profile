package com.personal.businessprofile.repository;

import com.personal.businessprofile.entity.BusinessProfileRevisionEntity;

import java.util.List;

public interface BusinessProfileRevisionDataAccess {

    List<BusinessProfileRevisionEntity> getBusinessProfileForRevision(String businessProfileId,
                                                                      Integer revision);

    BusinessProfileRevisionEntity save(BusinessProfileRevisionEntity businessProfileRevision);

    List<BusinessProfileRevisionEntity> getLatestBusinessProfileRevision(final String businessProfileId);

}
