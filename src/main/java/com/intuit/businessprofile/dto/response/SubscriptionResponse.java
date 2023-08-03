package com.intuit.businessprofile.dto.response;

import com.intuit.businessprofile.model.SubscriptionModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class SubscriptionResponse {

    protected String externalProductId;

    protected String businessProfileId;
    protected Boolean isActive;
}
