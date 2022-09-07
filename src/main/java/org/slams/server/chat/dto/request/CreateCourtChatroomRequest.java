package org.slams.server.chat.dto.request;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by yunyun on 2021/12/17.
 */

@Getter
public class CreateCourtChatroomRequest {
    private final Long courtId;

    @Builder
    public CreateCourtChatroomRequest(Long courtId){
        this.courtId = courtId;
    }
}
