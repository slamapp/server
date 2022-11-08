package org.slams.server.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CursorPageRequest {

	private int size;
	private String lastId;
	private Boolean isFirst;

	public Long getLastIdParedForLong() {
		return lastId == null ? null : Long.parseLong(lastId);
	}
}