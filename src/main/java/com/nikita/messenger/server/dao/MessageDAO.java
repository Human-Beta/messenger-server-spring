package com.nikita.messenger.server.dao;

import com.nikita.messenger.server.model.Message;

import java.util.Optional;

public interface MessageDAO {
    Optional<Message> getLastMessageFromChat(long chatId);
}
