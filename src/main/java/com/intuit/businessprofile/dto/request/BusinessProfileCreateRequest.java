package com.intuit.businessprofile.dto.request;

import com.intuit.businessprofile.model.TaxIdentifier;
import com.intuit.businessprofile.model.address.BusinessAddress;
import com.intuit.businessprofile.model.address.LegalAddress;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BusinessProfileCreateRequest {

    private String companyName;

    private String legalName;

    private LegalAddress legalAddress;

    private BusinessAddress businessAddress;

    private TaxIdentifier taxIdentifier;

    private String email;

    private String website;
}
