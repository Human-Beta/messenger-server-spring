package com.nikita.messenger.server.dto;

public class ChatDTO {
    private long id;
    private String name;
    private String imageUrl;
    private MessageDTO lastMessage;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MessageDTO getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(final MessageDTO lastMessage) {
        this.lastMessage = lastMessage;
    }
}
