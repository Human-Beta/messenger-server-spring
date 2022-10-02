package com.nikita.messenger.server.controller;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.dto.ChatDTO;
import com.nikita.messenger.server.dto.PaginationDTO;
import com.nikita.messenger.server.facade.ChatFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatControllerTest {
    private static final List<Long> EXCLUDED_IDS = of(42L);
    private static final int PAGE = 777;
    private static final int SIZE = 666;
    private static final String NAME = "name";

    @Spy
    @InjectMocks
    private ChatController controller;

    @Mock
    private ChatFacade chatFacade;

    private final ChatData chatData = new ChatData();
    private final ChatDTO chat = new ChatDTO();
    private final List<ChatDTO> chats = of(chat);
    private final PaginationDTO pagination = new PaginationDTO(PAGE, SIZE);

    @BeforeEach
    void setUp() {
        doReturn(chats).when(controller).mapAll(any(List.class), eq(ChatDTO.class));
    }

    @Test
    void shouldReturnChatsWhenGetChatsForCurrentUser() {
        when(chatFacade.getChatsForCurrentUserExcludeIds(EXCLUDED_IDS, SIZE)).thenReturn(of(chatData));

        final List<ChatDTO> returnedChats = controller.getChatsForCurrentUser(EXCLUDED_IDS, SIZE);

        assertThat(returnedChats).isSameAs(chats);
    }

    @Test
    void shouldReturnChatsWhenGetChatsWithNameStartsWith() {
        when(chatFacade.getChatsForCurrentUserWithNameStarsWith(NAME, PAGE, SIZE)).thenReturn(of(chatData));

        final List<ChatDTO> returnedChats = controller.getChatsWithNameStartsWith(NAME, pagination);

        assertThat(returnedChats).isSameAs(chats);
    }
}
