package com.nikita.messenger.server.dto;

public class ChatDTO extends AbstractDTO {
    private String name;
    private String chatName;
    private String imageUrl;
    private MessageDTO initialLastMessage;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(final String chatName) {
        this.chatName = chatName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MessageDTO getInitialLastMessage() {
        return initialLastMessage;
    }

    public void setInitialLastMessage(final MessageDTO initialLastMessage) {
        this.initialLastMessage = initialLastMessage;
    }
}
