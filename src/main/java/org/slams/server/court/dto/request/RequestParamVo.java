package org.slams.server.court.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class RequestParamVo {

    private String date;
    private String time;
    private List<Double> latitude;
    private List<Double> longitude;

}
