package com.nikita.messenger.server.populator;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PrivateChatPopulatorTest {
    private static final String USER_NAME = "userName";
    private static final String USER_NICKNAME = "userNickname";
    private static final String USER_AVATAR_URL = "userAvatarUrl";

    @InjectMocks
    private PrivateChatPopulator privateChatPopulator;

    private final ChatData chatData = new ChatData();
    private final User chatPartner = new User();

    @BeforeEach
    void setUp() {
        chatPartner.setName(USER_NAME);
        chatPartner.setNickname(USER_NICKNAME);
        chatPartner.setAvatarUrl(USER_AVATAR_URL);
    }

    @Test
    void shouldPopulateNameWhenConvertAndChatIsPrivate() {
        privateChatPopulator.populate(chatData, chatPartner);

        assertThat(chatData).isNotNull().returns(USER_NAME, ChatData::getName);
    }

    @Test
    void shouldPopulateChatNameWhenConvertAndChatIsPrivate() {
        privateChatPopulator.populate(chatData, chatPartner);

        assertThat(chatData).isNotNull().returns(USER_NICKNAME, ChatData::getChatName);
    }

    @Test
    void shouldPopulateImageUrlWhenConvertAndChatIsPrivate() {
        privateChatPopulator.populate(chatData, chatPartner);

        assertThat(chatData).isNotNull().returns(USER_AVATAR_URL, ChatData::getImageUrl);
    }
}
