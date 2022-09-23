package org.slams.server.common.apiTest;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class ListResponse<T> {

	private List<T> contents;
	private Map<String, Reference> reference;

	public ListResponse() {
		this.contents = new ArrayList<>();
	}
	public ListResponse(Map<String, Reference> reference) {
		this.reference = reference;
		this.contents = new ArrayList<>();
	}

	public void addContents(T contents) {
		this.contents.add(contents);
	}

}
