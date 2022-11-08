package org.slams.server.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ListResponse<T> {

	private List<T> contents;

	public ListResponse() {
		this.contents = new ArrayList<>();
	}

	public void addContents(T contents) {
		this.contents.add(contents);
	}

}
