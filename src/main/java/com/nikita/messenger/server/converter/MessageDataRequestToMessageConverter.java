package com.nikita.messenger.server.converter;

import com.nikita.messenger.server.data.MessageRequestData;
import com.nikita.messenger.server.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageDataRequestToMessageConverter extends AbstractConverter<MessageRequestData, Message> {
    @Override
    public Message convert(final MessageRequestData request) {
        final Message message = new Message();

        message.setLocalId(request.getLocalId());
        message.setValue(request.getValue());
        message.setChatId(request.getChatId());

        return message;
    }
}
