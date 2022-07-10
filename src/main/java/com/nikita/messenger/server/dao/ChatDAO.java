package com.nikita.messenger.server.dao;

import com.nikita.messenger.server.model.Chat;

import java.util.List;

public interface ChatDAO {
    List<Chat> getChatsFor(long id);
}
