package com.intuit.businessprofile.model;

import com.intuit.businessprofile.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class SubscriptionModel extends BaseEntity {

    public static final String EXTERNAL_PRODUCT_ID = "externalProductId";
    public static final String BUSINESS_PROFILE_ID = "businessProfileId";
    public static final String IS_ACTIVE = "isActive";

    protected String externalProductId;

    protected String businessProfileId;

    protected Boolean isActive;


}
