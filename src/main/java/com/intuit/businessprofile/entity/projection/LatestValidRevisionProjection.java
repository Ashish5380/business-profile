package com.intuit.businessprofile.entity.projection;

import com.intuit.businessprofile.entity.ProfileMappingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LatestValidRevisionProjection {

    private ObjectId _id;

    private Integer highestRevision;

    private List<ProfileMappingEntity> documents;
}
