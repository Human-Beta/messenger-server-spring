package com.nikita.messenger.server.facade;

import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.data.MessageRequestData;

import java.util.Date;
import java.util.List;

public interface MessageFacade {
    List<MessageData> getMessagesFromChat(long chatId, Date sinceDate, int size);

    MessageData saveMessageToChat(MessageRequestData messageRequestData);
}
