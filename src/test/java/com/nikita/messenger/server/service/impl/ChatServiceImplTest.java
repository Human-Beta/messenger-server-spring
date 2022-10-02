package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.repository.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.nikita.messenger.server.enums.ChatType.GROUP;
import static com.nikita.messenger.server.enums.ChatType.PRIVATE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Collections.singletonList;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.DESC;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {

    private static final long USER_ID = 123;
    private static final long USER_ID_2 = 124;
    private static final long CHAT_ID = 321;
    private static final List<Long> EXCLUDED_IDS = of(666L);
    private static final int SIZE = 42;
    private static final String CHAT_IS_NOT_PRIVATE_MESSAGE = "Chat '%d' must be a private chat with the only user!";
    private static final String NO_PARTNER_USER_MESSAGE = "Either there is the current user only, or there are no users, in the chat '%d'";

    @InjectMocks
    private ChatServiceImpl chatService;

    @Mock
    private ChatRepository chatRepository;
    @Captor
    private ArgumentCaptor<Pageable> paginationCaptor;

    private final User user = new User();
    private final User user2 = new User();
    private final Chat chat = new Chat();

    @BeforeEach
    void setUp() {
        user.setId(USER_ID);
        user2.setId(USER_ID_2);

        chat.setId(CHAT_ID);
        chat.setType(PRIVATE);
    }

    @Test
    void shouldReturnChatsWhenGetChatsForUserExcludeIds() {
        when(chatRepository.findAllByUsersIdAndIdNotIn(eq(USER_ID), eq(EXCLUDED_IDS), any(Pageable.class)))
                .thenReturn(of(chat));

        final List<Chat> foundChats = chatService.getChatsForUserExcludeIds(user, EXCLUDED_IDS, SIZE);

        assertThat(foundChats).containsExactly(chat);
    }

    @Test
    void shouldPassPageableWithSizeWhenGetChatsForUserExcludeIds() {
        chatService.getChatsForUserExcludeIds(user, EXCLUDED_IDS, SIZE);

        verify(chatRepository).findAllByUsersIdAndIdNotIn(eq(USER_ID), eq(EXCLUDED_IDS), paginationCaptor.capture());
        final Pageable pagination = paginationCaptor.getValue();

        assertThat(pagination.getPageSize()).isEqualTo(SIZE);
    }

    @Test
    void shouldPassPageableWithSortDirectionWhenGetChatsForUserExcludeIds() {
        chatService.getChatsForUserExcludeIds(user, EXCLUDED_IDS, SIZE);

        verify(chatRepository).findAllByUsersIdAndIdNotIn(eq(USER_ID), eq(EXCLUDED_IDS), paginationCaptor.capture());
        final Pageable pagination = paginationCaptor.getValue();

        assertThat(pagination.getSort().getOrderFor("lastMessage.date")).isNotNull()
                .returns(DESC, Sort.Order::getDirection);
    }

    @Test
    void shouldReturnAllChatsForUserWhenGetAllChatsFor() {
        when(chatRepository.findAllByUsersId(USER_ID)).thenReturn(of(chat));

        final List<Chat> chats = chatService.getAllChatsFor(user);

        assertThat(chats).containsExactly(chat);
    }

    @Test
    void shouldReturnAllPrivateChatsForUserWhenGetAllPrivateChatsFor() {
        when(chatRepository.findAllByUsersIdAndType(USER_ID, PRIVATE)).thenReturn(of(chat));

        final List<Chat> chats = chatService.getAllPrivateChatsFor(user);

        assertThat(chats).containsExactly(chat);
    }

    @Test
    void shouldReturnTrueWhenExists() {
        when(chatRepository.existsById(CHAT_ID)).thenReturn(TRUE);

        final boolean exists = chatService.exists(CHAT_ID);

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseWhenExists() {
        when(chatRepository.existsById(CHAT_ID)).thenReturn(FALSE);

        final boolean exists = chatService.exists(CHAT_ID);

        assertThat(exists).isFalse();
    }

    @Test
    void shouldReturnTrueWhenIsUserInChat() {
        when(chatRepository.isUserInChat(USER_ID, CHAT_ID)).thenReturn(TRUE);

        final boolean userInChat = chatService.isUserInChat(user, CHAT_ID);

        assertThat(userInChat).isTrue();
    }

    @Test
    void shouldReturnFalseWhenIsUserInChat() {
        when(chatRepository.isUserInChat(USER_ID, CHAT_ID)).thenReturn(FALSE);

        final boolean userInChat = chatService.isUserInChat(user, CHAT_ID);

        assertThat(userInChat).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenGetPartnerAndChatIsNotPrivate() {
        chat.setType(GROUP);

        assertThatThrownBy(() -> chatService.getPartner(chat, user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(CHAT_IS_NOT_PRIVATE_MESSAGE, CHAT_ID);
    }

    @Test
    void shouldThrowExceptionWhenGetPartnerAndChatHasNoUsers() {
        chat.setUsers(of());

        assertThatThrownBy(() -> chatService.getPartner(chat, user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(NO_PARTNER_USER_MESSAGE, CHAT_ID);
    }

    @Test
    void shouldThrowExceptionWhenGetPartnerAndChatHasNullUser() {
        chat.setUsers(singletonList(null));

        assertThatThrownBy(() -> chatService.getPartner(chat, user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(NO_PARTNER_USER_MESSAGE, CHAT_ID);
    }

    @Test
    void shouldThrowExceptionWhenGetPartnerAndChatHasCurrentUserOnly() {
        chat.setUsers(of(user));

        assertThatThrownBy(() -> chatService.getPartner(chat, user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(NO_PARTNER_USER_MESSAGE, CHAT_ID);
    }

    @Test
    void shouldReturnPartnerWhenGetPartner() {
        chat.setUsers(of(user, user2));

        final User partner = chatService.getPartner(chat, user);

        assertThat(partner).isSameAs(user2);
    }

    @Test
    void shouldReturnTrueWhenIsPrivateAndChatIsPrivate() {
        final boolean isPrivate = chatService.isPrivate(chat);

        assertThat(isPrivate).isTrue();
    }

    @Test
    void shouldReturnFalseWhenIsPrivateAndChatIsNotPrivate() {
        chat.setType(GROUP);

        final boolean isPrivate = chatService.isPrivate(chat);

        assertThat(isPrivate).isFalse();
    }

    @Test
    void shouldReturnTrueWhenIsGroupAndChatIsGroup() {
        chat.setType(GROUP);

        final boolean isGroup = chatService.isGroup(chat);

        assertThat(isGroup).isTrue();
    }

    @Test
    void shouldReturnFalseWhenIsGroupAndChatIsNotGroup() {
        final boolean isGroup = chatService.isGroup(chat);

        assertThat(isGroup).isFalse();
    }
}
