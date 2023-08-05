package com.personal.businessprofile.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    protected String externalProductId;

    protected String name;

    protected String description;
}
