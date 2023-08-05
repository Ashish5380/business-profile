package com.personal.businessprofile.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class Address {

    protected String line1;

    protected String line2;

    protected String city;

    protected String state;

    protected String country;

    protected String pinCode;
}
