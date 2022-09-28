package org.slams.server.common.apiTest;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
public class MetaReference {
    private List<String> meta;
    private Map<String, Reference> contents;

    public MetaReference() {
        this.meta = new ArrayList<>();
        this.contents = new HashMap<>();
    }

    public void put(String key, Reference reference) {
        meta.add(key);
        contents.put(key, reference);
    }
}
