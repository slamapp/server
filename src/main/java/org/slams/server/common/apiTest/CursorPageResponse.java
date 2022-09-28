package org.slams.server.common.apiTest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class CursorPageResponse<T> {

	private T contents;
	private MetaReference reference;
	private String lastId;

}
