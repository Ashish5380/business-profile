package com.personal.businessprofile.dto.response;


import com.personal.businessprofile.model.TaxIdentifier;
import com.personal.businessprofile.model.address.BusinessAddress;
import com.personal.businessprofile.model.address.LegalAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BusinessProfileResponse {

    protected String id;
    protected String companyName;

    protected String legalName;

    protected BusinessAddress businessAddress;

    protected LegalAddress legalAddress;

    protected String email;

    protected String website;

    protected TaxIdentifier taxIdentifier;

    protected Integer latestValidRevision;

}
