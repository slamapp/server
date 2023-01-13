package org.slams.server.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.apache.poi.ss.formula.functions.T;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeOfDataToRefreshResponse {

    private final TypeOfDataToRefresh type;


    private TypeOfDataToRefreshResponse(TypeOfDataToRefresh type){
        this.type = type;
    }

    public static TypeOfDataToRefreshResponse of (TypeOfDataToRefresh type) {
        return new TypeOfDataToRefreshResponse(type);
    }
}
