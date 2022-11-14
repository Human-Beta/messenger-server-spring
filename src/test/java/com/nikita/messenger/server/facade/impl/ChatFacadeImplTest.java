package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.exception.ChatAlreadyExistException;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.service.ChatService;
import com.nikita.messenger.server.service.SocketIOService;
import com.nikita.messenger.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatFacadeImplTest {

    private static final List<Long> EXCLUDED_IDS = of(666L);
    private static final int SIZE = 42;
    private static final String NICKNAME1 = "nickname1";
    private static final String NICKNAME2 = "nickname2";

    @Spy
    @InjectMocks
    private ChatFacadeImpl chatFacade;

    @Mock
    private ChatService chatService;
    @Mock
    private UserService userService;
    @Mock
    private SocketIOService socketIOService;

    private final Chat chat = new Chat();
    private final ChatData chatData = new ChatData();
    private final List<Chat> chats = of(chat);
    private final User user1 = new User();
    private final User user2 = new User();

    @Test
    void shouldReturnChatsWhenGetChatsForCurrentUserExcludeIds() {
        mockGetCurrentUser();
        when(chatService.getChatsForUserExcludeIds(user1, EXCLUDED_IDS, SIZE)).thenReturn(chats);
        doReturn(of(chatData)).when(chatFacade).convertAll(chats, ChatData.class);

        final List<ChatData> foundChats = chatFacade.getChatsForCurrentUserExcludeIds(EXCLUDED_IDS, SIZE);

        assertThat(foundChats).containsExactly(chatData);
    }

//    TODO: tests for getChatsForCurrentUserWithNameStarsWith

    @Test
    void shouldReturnCreatedChatWhenCreatePrivateChatWith() {
        mockGetCurrentUser();
        mockPrivateChat();
        mockCreatePrivateChat();
        mockConvertChatData();

        final ChatData createdChat = chatFacade.createPrivateChatWith(NICKNAME2);

        assertThat(createdChat).isSameAs(chatData);
    }

    @Test
    void shouldThrowExceptionWhenChatAlreadyExistsForUsers() {
        mockGetCurrentUser();
        mockUsers();
        mockPrivateChat();
        mockPrivateChatExistsFor(TRUE);

        assertThatThrownBy(() -> chatFacade.createPrivateChatWith(NICKNAME2))
                .isInstanceOf(ChatAlreadyExistException.class)
                .hasMessage("Private chat for users ['%s', '%s'] already exists", user1.getNickname(),
                            user2.getNickname());
    }

    @Test
    void shouldPassCorrectParamsToCreatePrivateChatForWhenCreatePrivateChatWith() {
        mockGetCurrentUser();
        mockPrivateChat();
        mockCreatePrivateChat();
        mockConvertChatData();

        chatFacade.createPrivateChatWith(NICKNAME2);

        verify(userService).getCurrentUser();
        verify(userService).getUserByNickname(NICKNAME2);
        verify(chatService).createPrivateChatFor(user1, user2);
    }

    @Test
    void shouldSendChatToSocketWhenCreatePrivateChatWith() {
        mockGetCurrentUser();
        mockPrivateChat();
        mockCreatePrivateChat();
        mockConvertChatData();

        chatFacade.createPrivateChatWith(NICKNAME2);

        verify(socketIOService).sendChat(chat);
    }

    private void mockGetCurrentUser() {
        when(userService.getCurrentUser()).thenReturn(user1);
    }

    private void mockPrivateChat() {
        when(userService.getUserByNickname(NICKNAME2)).thenReturn(user2);
        mockPrivateChatExistsFor(FALSE);
    }

    private void mockPrivateChatExistsFor(final boolean exists) {
        when(chatService.privateChatExistsFor(user1, user2)).thenReturn(exists);
    }

    private void mockCreatePrivateChat() {
        when(chatService.createPrivateChatFor(user1, user2)).thenReturn(chat);
    }

    private void mockConvertChatData() {
        doReturn(chatData).when(chatFacade).convert(chat, ChatData.class);
    }

    private void mockUsers() {
        user1.setName(NICKNAME1);
        user2.setName(NICKNAME2);
    }

}
