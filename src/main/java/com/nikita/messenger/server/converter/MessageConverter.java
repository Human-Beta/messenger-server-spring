package com.nikita.messenger.server.converter;

import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.model.Message;
import com.nikita.messenger.server.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageConverter extends AbstractConverter implements Converter<Message, MessageData> {

    @Override
    public MessageData convert(final Message message) {
        final MessageData messageData = new MessageData();

        messageData.setId(message.getId());
        messageData.setDate(message.getDate());
        setSender(messageData, message);
        messageData.setValue(message.getValue());

        return messageData;
    }

    private void setSender(final MessageData messageData, final Message message) {
        final User sender = message.getSender();

        messageData.setSender(getConversionService().convert(sender, UserData.class));
    }
}
