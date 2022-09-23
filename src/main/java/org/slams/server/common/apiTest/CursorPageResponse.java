package org.slams.server.common.apiTest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CursorPageResponse<T> extends CommonApiResponse {

	private T contents;
	private String lastId;

}
