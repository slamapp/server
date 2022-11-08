package org.slams.server.common.dto;

/**
 * Created by yunyun on 2022/01/24.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Follow extends BaseDto{
    private User sender;
    private User receiver;

    @Builder
    public Follow(
            User sender,
            User receiver
    ){
        this.sender = sender;
        this.receiver = receiver;
    }

}
