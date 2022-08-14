package com.nikita.messenger.server.data;

public class ChatData {
    private long id;
    private String name;
    private String chatName;
    private String imageUrl;
    private MessageData lastMessage;

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

    public MessageData getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(final MessageData lastMessage) {
        this.lastMessage = lastMessage;
    }
}
