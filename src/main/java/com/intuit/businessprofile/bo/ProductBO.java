package com.intuit.businessprofile.bo;

import com.intuit.businessprofile.model.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class ProductBO extends Product {
}
