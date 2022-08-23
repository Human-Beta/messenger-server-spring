package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.data.MessageRequestData;
import com.nikita.messenger.server.exception.ChatNotFoundException;
import com.nikita.messenger.server.facade.MessageFacade;
import com.nikita.messenger.server.model.Message;
import com.nikita.messenger.server.service.ChatService;
import com.nikita.messenger.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageFacadeImpl extends AbstractFacade implements MessageFacade {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ChatService chatService;

    @Override
//    TODO: place for transactional
    public List<MessageData> getMessagesFromChat(final long chatId, final int page, final int size) {
//        TODO: and check if current user allowed to get messages from the chat
        checkIfChatExists(chatId);

        final List<Message> messages = messageService.getMessagesFromChat(chatId, page, size);

        return convertAll(messages, MessageData.class);
    }

    private void checkIfChatExists(final long chatId) {
        if (!chatService.exists(chatId)) {
            throw new ChatNotFoundException(chatId);
        }
    }

    @Override
//    TODO: place for transactional
    public MessageData putMessageToChat(final MessageRequestData messageRequestData) {
//        TODO: and check if current user allowed to send message into the chat
        checkIfChatExists(messageRequestData.getChatId());

        final Message message = convert(messageRequestData, Message.class);

        final Message savedMessage = messageService.saveMessageToChat(message);
//        TODO: remove!
//        final Message savedMessage = messageService.getMessage(messageId)
//                .orElseThrow(() -> new IllegalStateException("There is no message with id " + messageId));

        return convert(savedMessage, MessageData.class);
    }
}
