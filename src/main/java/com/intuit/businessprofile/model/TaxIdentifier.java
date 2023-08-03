package com.intuit.businessprofile.model;

import com.intuit.businessprofile.enums.TaxIdentifierType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class TaxIdentifier {
    protected TaxIdentifierType type;
    protected String identifier;

}
