package com.nikita.messenger.server.facade;

import com.nikita.messenger.server.data.ChatData;

import java.util.List;

public interface ChatFacade {

    List<ChatData> getChatsForCurrentUserExcludeIds(List<Long> excludeIds, int size);

    List<ChatData> getChatsForCurrentUserWithNameStarsWith(String name, int page, int size);
}
