package org.slams.server.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Created by yunyun on 2022/01/24.
 */
@Getter
public class BaseDto {

    @ApiModelProperty(value = "생성일시", required = true)
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "수정일시", required = true)
    private LocalDateTime updatedAt;

    public BaseDto(){

    }
    public BaseDto(LocalDateTime createdAt, LocalDateTime updatedAt) {
    }
}
