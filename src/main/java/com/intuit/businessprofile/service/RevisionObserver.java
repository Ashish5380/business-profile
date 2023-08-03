package com.intuit.businessprofile.service;

import com.intuit.businessprofile.bo.BusinessProfileRevisionBO;
import com.intuit.businessprofile.model.BusinessProfileModel;

public interface RevisionObserver {

    /**
     * Update request to notify all mapped product about business-profile revision, where revision info is provided in
     * {@link BusinessProfileRevisionBO}
     * @param businessProfileId The unique identifier of the Business Profile.
     * @param revision Revision information for a given busines-profile identifier
     */
    void update(String businessProfileId, BusinessProfileRevisionBO revision);
}
