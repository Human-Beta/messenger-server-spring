package com.nikita.messenger.server.service;

import com.nikita.messenger.server.model.Message;

import java.util.Date;
import java.util.List;

public interface MessageService {
    List<Message> getMessagesFromChat(long chatId, final Date sinceDate, final int size);

    Message saveMessageToChat(Message message);
}
