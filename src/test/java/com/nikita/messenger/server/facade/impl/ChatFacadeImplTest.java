package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.service.ChatService;
import com.nikita.messenger.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatFacadeImplTest {

    private static final List<Long> EXCLUDED_IDS = of(666L);
    private static final int SIZE = 42;

    @Spy
    @InjectMocks
    private ChatFacadeImpl chatFacade;

    @Mock
    private ChatService chatService;
    @Mock
    private UserService userService;

    private final Chat chat = new Chat();
    private final ChatData chatData = new ChatData();
    private final List<Chat> chats = of(chat);
    private final User user = new User();

    @Test
    void shouldReturnChatsWhenGetChatsForCurrentUserExcludeIds() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(chatService.getChatsForUserExcludeIds(user, EXCLUDED_IDS, SIZE)).thenReturn(chats);
        doReturn(of(chatData)).when(chatFacade).convertAll(chats, ChatData.class);

        final List<ChatData> foundChats = chatFacade.getChatsForCurrentUserExcludeIds(EXCLUDED_IDS, SIZE);

        assertThat(foundChats).containsExactly(chatData);
    }

//    TODO: tests for getChatsForCurrentUserWithNameStarsWith
}
