package com.nikita.messenger.server.data;

public class MessageRequestData {
    private long chatId;
    private String value;

    public long getChatId() {
        return chatId;
    }

    public void setChatId(final long chatId) {
        this.chatId = chatId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
