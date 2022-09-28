package org.slams.server.common.apiTest;

import org.slams.server.common.dto.referenceDto.BaseReferenceDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reference<T extends BaseReferenceDto> extends HashMap<String, T> {

    public Reference<T> createReference(List<T> listToRefer) {
        Reference<T> reference = new Reference<T>();
        for (T data : listToRefer) {
            reference.put(data.getId(), data);
        }
        return reference;
    }

}
