package com.nikita.messenger.server.service;

import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Optional<Message> getMessage(long messageId);

    Optional<Message> getLastMessageFrom(Chat chat);

    List<Message> getMessagesFromChat(long chatId, final int page, final int size);

    long putMessageToChat(Message message);
}
