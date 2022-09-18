package com.nikita.messenger.server.service;

import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;

import java.util.List;

public interface ChatService {

    List<Chat> getChatsForUserExcludeIds(User user, List<Long> excludeIds, int size);

    List<Chat> getAllChatsFor(User user);

    boolean exists(long chatId);

    boolean isUserInChat(User user, long chatId);
}
