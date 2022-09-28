package org.slams.server.common.apiTest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;


@Getter
@AllArgsConstructor
public class DefaultApiResponse<T> {

	private T contents;
	private MetaReference reference;


}
