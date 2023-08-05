package com.personal.businessprofile.entity;

import com.personal.businessprofile.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@Document(collection = "product")
public class ProductEntity extends Product {
}
