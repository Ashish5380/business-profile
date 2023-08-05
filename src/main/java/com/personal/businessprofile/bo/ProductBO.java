package com.personal.businessprofile.bo;

import com.personal.businessprofile.model.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class ProductBO extends Product {
}
