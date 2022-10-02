package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.data.UserRegistrationData;
import com.nikita.messenger.server.exception.UserAlreadyExistException;
import com.nikita.messenger.server.facade.UserFacade;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.service.ChatService;
import com.nikita.messenger.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserFacadeImpl extends AbstractFacade implements UserFacade {
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    @Override
    public UserData getCurrentUser() {
        final User currentUser = getCurrentUserModel();

        return convert(currentUser, UserData.class);
    }

    private User getCurrentUserModel() {
        return userService.getCurrentUser();
    }

    @Override
    public UserData createUser(final UserRegistrationData userRegistration) {
//        TODO: do not need to check. Just to handle an error from DB that nickname exists
        if (userService.existsByNickname(userRegistration.getNickname())) {
            throw new UserAlreadyExistException(userRegistration.getNickname());
        }

        final User userToSave = convert(userRegistration, User.class);
        final User createdUser = userService.save(userToSave);

        return convert(createdUser, UserData.class);
    }

    @Override
    public List<UserData> getUnknownUsersByNickname(final String nickname, final int page, final int size) {
        final User currentUser = getCurrentUserModel();

        final List<Long> excludedIds = new ArrayList<>();
        excludedIds.add(currentUser.getId());

        addAllPrivateChatUsers(currentUser, excludedIds);

        return convertAll(userService.getUsersByNicknameExcludeIds(nickname, excludedIds, page, size), UserData.class);
    }

    private void addAllPrivateChatUsers(final User currentUser, final List<Long> excludedIds) {
        final List<Chat> privateChats = chatService.getAllPrivateChatsFor(currentUser);

        privateChats.stream()
                .map(chat -> getPartner(chat, currentUser))
                .map(User::getId)
                .forEach(excludedIds::add);
    }

    private User getPartner(final Chat chat, final User user) {
        return chatService.getPartner(chat, user);
    }
}
