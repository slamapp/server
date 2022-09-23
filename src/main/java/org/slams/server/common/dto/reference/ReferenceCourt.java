package org.slams.server.common.dto.reference;

import lombok.Builder;
import lombok.Getter;
import org.slams.server.common.dto.Court;
import org.slams.server.court.entity.Texture;

@Getter
public class ReferenceCourt extends Court implements BaseReferenceDto{

    @Builder
    public ReferenceCourt(
            String id,
            String name,
            double latitude,
            double longitude,
            String image,
            int basketCount,
            Texture texture
    ) {
        super(id, name, latitude, longitude, image, basketCount, texture);
    }
}
