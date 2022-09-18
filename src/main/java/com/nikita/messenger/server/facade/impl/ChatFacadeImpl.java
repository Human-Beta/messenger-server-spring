package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.facade.ChatFacade;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.service.ChatService;
import com.nikita.messenger.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatFacadeImpl extends AbstractFacade implements ChatFacade {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

    @Override
    public List<ChatData> getChatsForCurrentUserExcludeIds(final List<Long> excludeIds, final int size) {
        final User user = userService.getCurrentUser();

        final List<Chat> chats = chatService.getChatsForUserExcludeIds(user, excludeIds, size);

        return convertAll(chats, ChatData.class);
    }
}
