package com.nikita.messenger.server.dao;

import com.nikita.messenger.server.model.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatDAO {
    List<Chat> getChatsFor(long id, final int page, final int size);

    Optional<Chat> getChat(long chatId);
}
