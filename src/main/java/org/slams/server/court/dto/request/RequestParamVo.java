package org.slams.server.court.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RequestParamVo {

    private String date;
    private String time;
    private List<Double> latitude;
    private List<Double> longitude;

}
