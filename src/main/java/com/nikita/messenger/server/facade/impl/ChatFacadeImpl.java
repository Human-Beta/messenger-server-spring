package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.exception.ChatAlreadyExistException;
import com.nikita.messenger.server.facade.ChatFacade;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.service.ChatService;
import com.nikita.messenger.server.service.SocketIOService;
import com.nikita.messenger.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;

@Component
public class ChatFacadeImpl extends AbstractFacade implements ChatFacade {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;
    @Autowired
    private SocketIOService socketIOService;

    @Override
    public List<ChatData> getChatsForCurrentUserExcludeIds(final List<Long> excludedIds, final int size) {
        final User user = getCurrentUser();

        final List<Chat> chats = chatService.getChatsForUserExcludeIds(user, excludedIds, size);

        return convertAll(chats, ChatData.class);
    }

    private User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @Override
//    TODO: is it OK or need to be refactored? can I make SQL query for it?
    public List<ChatData> getChatsForCurrentUserWithNameStarsWith(final String name, final int page, final int size) {
        final User user = getCurrentUser();

        final List<Chat> chatModels = chatService.getAllChatsFor(user);
        final List<ChatData> chats = convertAll(chatModels, ChatData.class);

        final int skipCount = (page - 1) * size;
        return chats.stream()
                .filter(chat -> hasNameOrChatNameStartsWith(chat, name))
                .sorted(comparing(ChatData::getId))
                .skip(skipCount)
                .limit(size)
                .collect(toList());
    }

    private static boolean hasNameOrChatNameStartsWith(final ChatData chat, final String name) {
        return startsWithIgnoreCase(chat.getName(), name)
                || startsWithIgnoreCase(chat.getChatName(), name);
    }

    @Override
    public ChatData createPrivateChatWith(final String nickname) {
        final User user1 = getCurrentUser();
        final User user2 = userService.getUserByNickname(nickname);

        checkIfPrivateChatWithUsersNotExists(user1, user2);

        final Chat chat = chatService.createPrivateChatFor(user1, user2);

        socketIOService.sendChat(chat);

        return convert(chat, ChatData.class);
    }

    private void checkIfPrivateChatWithUsersNotExists(final User user1, final User user2) {
        if (chatService.privateChatExistsFor(user1, user2)) {
            throw new ChatAlreadyExistException(user1, user2);
        }
    }
}
