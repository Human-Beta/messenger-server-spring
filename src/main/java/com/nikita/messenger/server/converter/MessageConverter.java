package com.nikita.messenger.server.converter;

import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageConverter extends AbstractConverter<Message, MessageData> {

    @Override
    public MessageData convert(final Message message) {
        final MessageData messageData = new MessageData();

        messageData.setId(message.getId());
        messageData.setDate(message.getDate());
        messageData.setSenderId(message.getSenderId());
        messageData.setChatId(message.getChatId());
        messageData.setValue(message.getValue());

        return messageData;
    }
}
