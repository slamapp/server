package org.slams.server.common.apiTest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;


@Getter
@AllArgsConstructor
public class ApiResponse<T> {

	private T contents;
	private Map<String, Reference> reference;
	private String lastId;

}
