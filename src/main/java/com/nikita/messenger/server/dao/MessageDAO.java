package com.nikita.messenger.server.dao;

import com.nikita.messenger.server.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageDAO {
    Optional<Message> getMessage(long messageId);

    Optional<Message> getLastMessageFromChat(long chatId);

    List<Message> getMessagesFromChat(long chatId, final int page, final int size);

    long putMessageToChat(Message message);
}
