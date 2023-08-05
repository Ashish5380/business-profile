package com.personal.businessprofile.model;

import com.personal.businessprofile.entity.BaseEntity;
import com.personal.businessprofile.enums.ValidationStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProfileMapping extends BaseEntity {

    public static final String EXTERNAL_PRODUCT_ID = "externalProductId";
    public static final String BUSINESS_PROFILE_ID = "businessProfileId";

    public static final String REVISION = "revision";

    public static final String VALIDATION_STATUS = "validationStatus";

    public static final String VALIDATION_ERROR = "validationError";

    protected String businessProfileId;

    protected Integer revision;

    protected String externalProductId;

    protected ValidationStatus validationStatus;

    protected String validationError;
}
