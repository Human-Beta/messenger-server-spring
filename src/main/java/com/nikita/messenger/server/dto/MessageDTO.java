package com.nikita.messenger.server.dto;

import java.util.Date;

public class MessageDTO {
    private String id;
    private String senderId;
    private String value;
    private Date time;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(final String senderId) {
        this.senderId = senderId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(final Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{" +
                "senderId='" + senderId + '\'' +
                ", value='" + value + '\'' +
                ", time=" + time +
                '}';
    }
}
