package com.nikita.messenger.server.converter;

import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.model.Message;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageReverseConverter implements Converter<MessageData, Message> {
    @Override
    public Message convert(final MessageData messageData) {
        final Message message = new Message();

        message.setId(messageData.getId());
        message.setLocalId(messageData.getLocalId());
        message.setValue(messageData.getValue());
        message.setChatId(messageData.getChatId());
        message.setSenderId(messageData.getSenderId());
        message.setDate(messageData.getDate());

        return message;
    }
}
