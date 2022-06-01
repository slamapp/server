package org.slams.server.court.dto.response;

import lombok.*;
import org.slams.server.common.api.BaseResponse;
import org.slams.server.court.entity.Court;
import org.slams.server.court.entity.Texture;

import java.time.LocalDateTime;

@Getter
public class CourtInMapDto extends BaseResponse {

    private String id;
    private String name;
    private double latitude;
    private double longitude;

    private CourtInMapDto(String id, String name, double latitude, double longitude, LocalDateTime createdAt, LocalDateTime updatedAt){
        super(createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static CourtInMapDto toDto(Court court){
        return new CourtInMapDto(court.getId().toString(), court.getName(), court.getLatitude(), court.getLongitude(),
            court.getCreatedAt(), court.getUpdateAt());
    }

}
