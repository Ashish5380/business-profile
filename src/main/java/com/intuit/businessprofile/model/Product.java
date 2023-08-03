package com.intuit.businessprofile.model;

import com.intuit.businessprofile.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class Product extends BaseEntity {

    protected String externalProductId;

    protected String name;

    protected String description;
}
