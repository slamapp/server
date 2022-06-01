package org.slams.server.favorite.dto.request;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteInsertRequestDto {

    Long courtId;


//    @Builder
//    public Favorite insertRequestDtoToEntity(User user, Court court) {
//        return Favorite.builder()
//                .user(user)
//                .court(court)
//                .build();
//    }

}
