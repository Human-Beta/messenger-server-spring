package com.nikita.messenger.server.data;

import java.util.Date;

public class MessageData {
    private long id;
    private UserData sender;
    private String value;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public UserData getSender() {
        return sender;
    }

    public void setSender(final UserData sender) {
        this.sender = sender;
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
