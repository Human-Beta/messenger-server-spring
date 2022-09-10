package com.nikita.messenger.server.service;

import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;

import java.util.List;

public interface ChatService {

    List<Chat> getChatsFor(User user, final int page, final int size);

    List<Chat> getAllChatsFor(User user);

    boolean exists(long chatId);

    boolean isUserInChat(User user, long chatId);
}
