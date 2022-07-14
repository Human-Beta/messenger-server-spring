package com.nikita.messenger.server.dto;

import java.util.Date;

public class MessageDTO extends AbstractDTO {
    private long senderId;
    private long chatId;
    private String value;
    private Date date;

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(final long senderId) {
        this.senderId = senderId;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "{" +
                "senderId='" + senderId + '\'' +
                ", value='" + value + '\'' +
                ", date=" + date +
                '}';
    }
}
