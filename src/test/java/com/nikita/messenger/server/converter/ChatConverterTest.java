package com.nikita.messenger.server.converter;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.Message;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.service.ChatService;
import com.nikita.messenger.server.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;

import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatConverterTest {

    private static final long ID = 42;
    private static final String USER_NAME = "userName";
    private static final String USER_NICKNAME = "userNickname";
    private static final String USER_AVATAR_URL = "userAvatarUrl";

    @Spy
    @InjectMocks
    private ChatConverter converter;

    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private UserService userService;
    @Mock
    private ChatService chatService;
    @Mock
    private ConversionService conversionService;

    private final Chat chat = new Chat();
    private final Message message = new Message();
    private final MessageData messageData = new MessageData();
    private final User currentUser = new User();
    private final User partner = new User();

    @BeforeEach
    void setUp() {
        chat.setId(ID);
        chat.setLastMessage(message);

        when(applicationContext.getBean(ConversionService.class)).thenReturn(conversionService);
        when(conversionService.convert(message, MessageData.class)).thenReturn(messageData);
    }

    @Test
    void shouldPopulateIdWhenConvert() {
        final ChatData chatData = converter.convert(chat);

        assertThat(chatData).isNotNull().returns(ID, ChatData::getId);
    }

    @Test
    void shouldPopulateNameWhenConvertAndChatIsPrivate() {
        mockPrivateChat();

        final ChatData chatData = converter.convert(chat);

        assertThat(chatData).isNotNull().returns(USER_NAME, ChatData::getName);
    }

    @Test
    void shouldPopulateChatNameWhenConvertAndChatIsPrivate() {
        mockPrivateChat();

        final ChatData chatData = converter.convert(chat);

        assertThat(chatData).isNotNull().returns(USER_NICKNAME, ChatData::getChatName);
    }

    @Test
    void shouldPopulateImageUrlWhenConvertAndChatIsPrivate() {
        mockPrivateChat();

        final ChatData chatData = converter.convert(chat);

        assertThat(chatData).isNotNull().returns(USER_AVATAR_URL, ChatData::getImageUrl);
    }

    @Test
    void shouldPopulateLastMessageWhenConvert() {
        final ChatData chatData = converter.convert(chat);

        assertThat(chatData).isNotNull().returns(messageData, ChatData::getLastMessage);
    }

    @Test
    void shouldNotPopulateNameWhenConvertAndChatIsGroup() {
        mockGroupChat();

        final ChatData chatData = converter.convert(chat);

        assertThat(chatData).isNotNull().returns(null, ChatData::getName);
    }

    @Test
    void shouldNotPopulateChatNameWhenConvertAndChatIsGroup() {
        mockGroupChat();

        final ChatData chatData = converter.convert(chat);

        assertThat(chatData).isNotNull().returns(null, ChatData::getChatName);
    }

    @Test
    void shouldNotPopulateImageUrlWhenConvertAndChatIsGroup() {
        mockGroupChat();

        final ChatData chatData = converter.convert(chat);

        assertThat(chatData).isNotNull().returns(null, ChatData::getImageUrl);
    }

    private void mockPrivateChat() {
        partner.setName(USER_NAME);
        partner.setNickname(USER_NICKNAME);
        partner.setAvatarUrl(USER_AVATAR_URL);

        when(chatService.isPrivate(chat)).thenReturn(TRUE);
        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(chatService.getPartner(chat, currentUser)).thenReturn(partner);
    }

    private void mockGroupChat() {
        when(chatService.isGroup(chat)).thenReturn(TRUE);
    }
}
