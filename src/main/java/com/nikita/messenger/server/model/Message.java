package com.nikita.messenger.server.model;

import java.util.Date;

public class Message {
    private long id;
    private User sender;
//    TODO: lazy load?
    private Chat chat;
    private String value;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(final User sender) {
        this.sender = sender;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(final Chat chat) {
        this.chat = chat;
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
}
