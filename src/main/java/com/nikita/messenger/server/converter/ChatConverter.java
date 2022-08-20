package com.nikita.messenger.server.converter;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.service.MessageService;
import com.nikita.messenger.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.nikita.messenger.server.enums.ChatType.GROUP;
import static com.nikita.messenger.server.enums.ChatType.PRIVATE;
import static java.lang.String.format;

@Component
public class ChatConverter extends AbstractConverter<Chat, ChatData> {
    private static final String NO_PARTNER_USER_MESSAGE = "Either there is the current user only, or there are no users, in the chat '%s'";

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @Override
    public ChatData convert(final Chat chat) {
        final ChatData chatData = new ChatData();

        chatData.setId(chat.getId());
        if (chat.getType() == PRIVATE) {
            final User chatPartnerUser = getChatPartnerUser(chat);

            setName(chatPartnerUser, chatData);
            setChatName(chatPartnerUser, chatData);
            setImageUrl(chatPartnerUser, chatData);
        } else if (chat.getType() == GROUP) {
//            TODO:
//            chatData.setName(chat.getName());
//            chatData.setImageUrl(chat.getImageUrl())
        }
        setLastMessage(chat, chatData);

        return chatData;
    }

    private void setName(final User user, final ChatData chatData) {
        chatData.setName(user.getName());
    }

    private void setChatName(final User user, final ChatData chatData) {
        chatData.setChatName(user.getNickname());
    }

    private User getChatPartnerUser(final Chat chat) {
        final User currentUser = userService.getCurrentUser();

        return chat.getUsers().stream()
                .filter(Objects::nonNull)
                .filter(user -> !user.equals(currentUser))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format(NO_PARTNER_USER_MESSAGE, chat.getId())));
    }

    private void setLastMessage(final Chat chat, final ChatData chatData) {
        messageService.getLastMessageFrom(chat)
                .map(message -> getConversionService().convert(message, MessageData.class))
                .ifPresent(chatData::setLastMessage);
    }

    private void setImageUrl(final User user, final ChatData chatData) {
        chatData.setImageUrl(user.getAvatarUrl());
    }
}
