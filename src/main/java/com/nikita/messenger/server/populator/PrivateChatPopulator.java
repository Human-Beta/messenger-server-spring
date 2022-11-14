package com.nikita.messenger.server.populator;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.model.User;
import org.springframework.stereotype.Component;

@Component
public class PrivateChatPopulator {
    public void populate(final ChatData chatData, final User chatPartner) {
        setName(chatData, chatPartner);
        setChatName(chatData, chatPartner);
        setImageUrl(chatData, chatPartner);
    }

    private void setName(final ChatData chatData, final User chatPartner) {
        chatData.setName(chatPartner.getName());
    }

    private void setChatName(final ChatData chatData, final User chatPartner) {
        chatData.setChatName(chatPartner.getNickname());
    }

    private void setImageUrl(final ChatData chatData, final User chatPartner) {
        chatData.setImageUrl(chatPartner.getAvatarUrl());
    }
}
