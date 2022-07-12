package com.nikita.messenger.server.service;

import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.model.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatService {

    List<Chat> getChatsFor(UserData user, final int page, final int size);

    Optional<Chat> getChat(long chatId);
}
