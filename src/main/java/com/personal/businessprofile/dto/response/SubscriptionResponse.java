package com.personal.businessprofile.dto.response;

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
