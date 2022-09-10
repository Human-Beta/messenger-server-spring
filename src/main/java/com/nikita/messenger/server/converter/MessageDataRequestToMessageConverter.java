package com.nikita.messenger.server.converter;

import com.nikita.messenger.server.data.MessageRequestData;
import com.nikita.messenger.server.model.Message;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageDataRequestToMessageConverter implements Converter<MessageRequestData, Message> {
    @Override
    public Message convert(final MessageRequestData request) {
        final Message message = new Message();

        message.setValue(request.getValue());
        message.setChatId(request.getChatId());

        return message;
    }
}
