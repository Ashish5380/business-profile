package com.personal.businessprofile.model;

import com.personal.businessprofile.enums.TaxIdentifierType;
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
