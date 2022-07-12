package com.nikita.messenger.server.facade;

import com.nikita.messenger.server.data.MessageData;

import java.util.List;

public interface MessageFacade {
    List<MessageData> getMessagesFromChat(long chatId);
}
