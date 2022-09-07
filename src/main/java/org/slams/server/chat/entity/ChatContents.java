package org.slams.server.chat.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.chat.dto.common.ChatType;
import org.slams.server.common.BaseEntity;
import org.slams.server.court.entity.Court;
import org.slams.server.user.entity.User;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by yunyun on 2021/12/03.
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chat_contents")
public class ChatContents extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private ChatType chatType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "court_id", nullable = false, referencedColumnName = "id")
    private Court court;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loudspeaker_id", referencedColumnName = "id")
    private ChatLoudSpeakerContent chatLoudSpeakerContent;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    private ChatConversationContent chatConversationContent;



    @Builder
    public ChatContents(
            Long id,
            ChatType chatType,
            Court court,
            User user,
            ChatConversationContent conversationContent,
            ChatLoudSpeakerContent chatLoudSpeakerContent
    ){
        checkArgument(id != null, "id는 null을 허용하지 않습니다.");
        checkArgument(chatType != null, "chatType는 null을 허용하지 않습니다.");
        checkArgument(court != null, "court 정보는 null을 허용하지 않습니다.");
        checkArgument(user != null, "user 정보는 null을 허용하지 않습니다.");

        this.id = id;
        this.chatType = chatType;
        this.court = court;
        this.user = user;
        this.chatConversationContent = conversationContent;
        this.chatLoudSpeakerContent = chatLoudSpeakerContent;
    }

    private ChatContents(
            ChatType chatType,
            Court court,
            User user,
            ChatConversationContent chatConversationContent
    ){
        checkArgument(chatType != null, "chatType는 null을 허용하지 않습니다.");
        checkArgument(court != null, "court 정보는 null을 허용하지 않습니다.");
        checkArgument(user != null, "user 정보는 null을 허용하지 않습니다.");

        this.chatType = chatType;
        this.court = court;
        this.user = user;
        this.chatConversationContent = chatConversationContent;
    }

    private ChatContents(
            ChatType chatType,
            Court court,
            User user,
            ChatLoudSpeakerContent chatLoudSpeakerContent
    ){
        checkArgument(chatType != null, "chatType는 null을 허용하지 않습니다.");
        checkArgument(court != null, "court는 null을 허용하지 않습니다.");
        checkArgument(user != null, "user 정보는 null을 허용하지 않습니다.");
        this.chatType = chatType;
        this.court = court;
        this.user = user;
        this.chatLoudSpeakerContent = chatLoudSpeakerContent;
    }

    public static ChatContents createConversationContent(
            ChatType chatType,
            Court court,
            User user,
            ChatConversationContent conversationContent
    ){
        return new ChatContents(chatType, court, user, conversationContent);
    }

    public static ChatContents createLoudspeakerContent(
            ChatType chatType,
            Court court,
            User user,
            ChatLoudSpeakerContent chatLoudSpeakerContent
    ){
        return new ChatContents(chatType, court, user, chatLoudSpeakerContent);
    }

}
