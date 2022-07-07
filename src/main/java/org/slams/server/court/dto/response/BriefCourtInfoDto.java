package org.slams.server.court.dto.response;

import lombok.Getter;
import org.slams.server.court.entity.Court;

@Getter
public class BriefCourtInfoDto {

	private String id;
	private String name;

	private BriefCourtInfoDto(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public static BriefCourtInfoDto toDto(Court court){
		return new BriefCourtInfoDto(court.getId().toString(), court.getName());
	}

}
