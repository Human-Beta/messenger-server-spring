package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.enums.ChatType;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.repository.ChatRepository;
import com.nikita.messenger.server.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.nikita.messenger.server.enums.ChatType.PRIVATE;
import static java.lang.String.format;
import static java.util.List.of;
import static org.springframework.data.domain.PageRequest.ofSize;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class ChatServiceImpl implements ChatService {
    private static final String CHAT_IS_NOT_PRIVATE_MESSAGE = "Chat '%d' must be a private chat with the only user!";
    private static final String NO_PARTNER_USER_MESSAGE = "Either there is the current user only, or there are no users, in the chat '%d'";

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat createPrivateChatFor(final User user1, final User user2) {
        if (user1.equals(user2)) {
            throw new IllegalArgumentException("Cannot create a private chat with two same users!");
        }

        final Chat chat = new Chat();
        chat.setType(PRIVATE);
        chat.setUsers(of(user1, user2));

        return chatRepository.save(chat);
    }

    @Override
    public List<Chat> getChatsForUserExcludeIds(final User user, final List<Long> excludedIds, final int size) {
        return chatRepository.findAllByUsersIdAndIdNotIn(user.getId(), excludedIds,
                                                         ofSize(size).withSort(DESC, "lastMessage.date"));
    }

    @Override
    public List<Chat> getAllChatsFor(final User user) {
        return chatRepository.findAllByUsersId(user.getId());
    }

    @Override
    public List<Chat> getAllPrivateChatsFor(final User user) {
        return chatRepository.findAllByUsersIdAndType(user.getId(), PRIVATE);
    }

    @Override
    public boolean exists(final long chatId) {
        return chatRepository.existsById(chatId);
    }

    @Override
    public boolean isUserInChat(final User user, final long chatId) {
        return chatRepository.isUserInChat(user.getId(), chatId);
    }

    @Override
    public User getPartner(final Chat chat, final User currentUser) {
        checkIfPrivate(chat);

        return chat.getUsers().stream()
                .filter(Objects::nonNull)
                .filter(user -> !user.equals(currentUser))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format(NO_PARTNER_USER_MESSAGE, chat.getId())));
    }

    private void checkIfPrivate(final Chat chat) {
        if (isNotPrivate(chat)) {
            throw new IllegalStateException(format(CHAT_IS_NOT_PRIVATE_MESSAGE, chat.getId()));
        }
    }

    @Override
    public boolean isPrivate(final Chat chat) {
        return chat.getType() == ChatType.PRIVATE;
    }

    @Override
    public boolean isGroup(final Chat chat) {
        return chat.getType() == ChatType.GROUP;
    }

    @Override
    public boolean privateChatExistsFor(final User user1, final User user2) {
        return chatRepository.privateChatExistsForUsers(user1.getId(), user2.getId());
    }
}
