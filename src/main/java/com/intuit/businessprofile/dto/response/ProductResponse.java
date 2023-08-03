package com.intuit.businessprofile.dto.response;

import com.intuit.businessprofile.model.Product;
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
public class ProductResponse {
    protected String externalProductId;

    protected String name;

    protected String description;
}
