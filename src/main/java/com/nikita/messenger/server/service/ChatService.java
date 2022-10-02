package com.nikita.messenger.server.service;

import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;

import java.util.List;

public interface ChatService {

    List<Chat> getChatsForUserExcludeIds(User user, List<Long> excludedIds, int size);

    List<Chat> getAllChatsFor(User user);

    List<Chat> getAllPrivateChatsFor(User user);

    boolean exists(long chatId);

    boolean isUserInChat(User user, long chatId);

    User getPartner(Chat chat, User currentUser);

    boolean isPrivate(Chat chat);

    default boolean isNotPrivate(final Chat chat) {
        return !isPrivate(chat);
    }

    boolean isGroup(Chat chat);
}
