package org.slams.server.favorite.dto.request;

import lombok.*;
import org.slams.server.court.entity.Court;
import org.slams.server.favorite.entity.Favorite;
import org.slams.server.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteInsertRequest {

    private String courtId;

    @Builder
    public FavoriteInsertRequest(String courtId) {
        this.courtId = courtId;
    }

    public Favorite toEntity(Court court, User user){
        return new Favorite(court, user);
    }

}
