package com.nikita.messenger.server.facade;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.data.UserData;

import java.util.List;

public interface ChatFacade {

    List<ChatData> getChatsFor(UserData user);
}
