package com.personal.businessprofile.entity;

import com.personal.businessprofile.model.BusinessProfileModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "business_profile")
public class BusinessProfileEntity extends BusinessProfileModel {

    protected Integer latestValidRevision;

}
