package com.nikita.messenger.server.converter;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.populator.PrivateChatPopulator;
import com.nikita.messenger.server.service.ChatService;
import com.nikita.messenger.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatConverter extends AbstractConverter<Chat, ChatData> {
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private PrivateChatPopulator privateChatPopulator;

    @Override
    public ChatData convert(final Chat chat) {
        final ChatData chatData = new ChatData();

        chatData.setId(chat.getId());
        if (isPrivate(chat)) {
            final User chatPartnerUser = getPartnerForCurrentUser(chat);

            privateChatPopulator.populate(chatData, chatPartnerUser);
        } else if (isGroup(chat)) {
//            TODO:
//             chatData.setName(chat.getName());
//             chatData.setChatName(chat.getChatName());
//             chatData.setImageUrl(chat.getImageUrl());
        }
        setLastMessage(chat, chatData);

        return chatData;
    }

    private boolean isPrivate(final Chat chat) {
        return chatService.isPrivate(chat);
    }

    private boolean isGroup(final Chat chat) {
        return chatService.isGroup(chat);
    }

    private User getPartnerForCurrentUser(final Chat chat) {
        return chatService.getPartner(chat, userService.getCurrentUser());
    }

    private void setLastMessage(final Chat chat, final ChatData chatData) {
        final MessageData messageData = getConversionService().convert(chat.getLastMessage(), MessageData.class);
        chatData.setLastMessage(messageData);
    }
}
