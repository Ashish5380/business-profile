package com.personal.businessprofile.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.personal.businessprofile.model.TaxIdentifier;
import com.personal.businessprofile.model.address.BusinessAddress;
import com.personal.businessprofile.model.address.LegalAddress;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessProfileUpdateRequest {

    String companyName;

    String legalName;

    LegalAddress legalAddress;

    BusinessAddress businessAddress;

    TaxIdentifier taxIdentifier;

    String email;

    String website;
}
