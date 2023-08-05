package com.personal.businessprofile.model;

import com.personal.businessprofile.entity.BaseEntity;
import com.personal.businessprofile.model.address.BusinessAddress;
import com.personal.businessprofile.model.address.LegalAddress;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Embedded;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class BusinessProfileModel extends BaseEntity {

    protected String companyName;

    protected String legalName;

    @Embedded
    protected BusinessAddress businessAddress;

    @Embedded
    protected LegalAddress legalAddress;

    protected String email;

    protected String website;

    protected TaxIdentifier taxIdentifier;

}
