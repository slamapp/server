package org.slams.server.common.api;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CursorPageRequest {

	private int size;
	private String lastId;
	private Boolean isFirst;

	@ApiModelProperty(hidden = true)
	public Long getLastIdParedForLong() {
		return lastId == null ? null : Long.parseLong(lastId);
	}
}