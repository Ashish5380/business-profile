package com.personal.businessprofile.model;

import com.personal.businessprofile.entity.BaseEntity;
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
