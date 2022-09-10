package com.nikita.messenger.server.dto;

public class MessageRequestDTO extends AbstractDTO {
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

    @Override
    public String toString() {
        return "MessageRequestDTO{" +
                "chatId=" + chatId +
                ", value='" + value + '\'' +
                '}';
    }
}
