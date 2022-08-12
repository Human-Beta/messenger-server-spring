package com.nikita.messenger.server.facade;

import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.data.MessageRequestData;

import java.util.List;

public interface MessageFacade {
    List<MessageData> getMessagesFromChat(long chatId, final int page, final int size);

    MessageData putMessageToChat(MessageRequestData messageRequestData);
}
