package com.personal.businessprofile.service;

import com.personal.businessprofile.bo.BusinessProfileRevisionBO;

public interface RevisionObserver {

    /**
     * Update request to notify all mapped product about business-profile revision, where revision info is provided in
     * {@link BusinessProfileRevisionBO}
     * @param businessProfileId The unique identifier of the Business Profile.
     * @param revision Revision information for a given busines-profile identifier
     */
    void update(String businessProfileId, BusinessProfileRevisionBO revision);
}
