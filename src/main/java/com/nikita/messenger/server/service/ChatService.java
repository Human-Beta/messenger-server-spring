package com.nikita.messenger.server.service;

import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.model.Chat;

import java.util.List;

public interface ChatService {

    List<Chat> getChatsFor(UserData user);
}
