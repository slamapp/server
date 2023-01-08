package org.slams.server.chat.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.common.BaseEntity;

import javax.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by yunyun on 2021/12/17.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chat_loudspeaker_contents")
public class ChatLoudSpeakerContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable = false)
    private int startTime;

    @Column(nullable = false)
    private LocalDateTime startDate;


    @Builder
    public ChatLoudSpeakerContent(Long id, LocalDateTime startDate){
        this.id = id;
        this.startDate = startDate;
    }

    private ChatLoudSpeakerContent(LocalDateTime startDate){
        this.startDate = startDate;
    }

    public static ChatLoudSpeakerContent of(LocalDateTime startDate){
        return new ChatLoudSpeakerContent(startDate);
    }
}
