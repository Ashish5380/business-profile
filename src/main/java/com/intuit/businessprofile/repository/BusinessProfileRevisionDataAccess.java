package com.intuit.businessprofile.repository;

import com.intuit.businessprofile.entity.BusinessProfileRevisionEntity;

import java.util.List;

public interface BusinessProfileRevisionDataAccess {

    List<BusinessProfileRevisionEntity> getBusinessProfileForRevision(String businessProfileId,
                                                                      Integer revision);

    BusinessProfileRevisionEntity save(BusinessProfileRevisionEntity businessProfileRevision);

    List<BusinessProfileRevisionEntity> getLatestBusinessProfileRevision(final String businessProfileId);

}
