package com.nikita.messenger.server.dto;

import java.util.Date;

public class MessageDTO {
    private long id;
    private long senderId;
    private String value;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(final long senderId) {
        this.senderId = senderId;
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
