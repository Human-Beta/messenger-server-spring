package com.nikita.messenger.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "local_id")
    private long localId;
    @Column(name = "sender_id")
    private long senderId;
    @Column(name = "chat_id")
    private long chatId;
    @Column
    private String value;
    @Column
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getLocalId() {
        return localId;
    }

    public void setLocalId(final long localId) {
        this.localId = localId;
    }

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
}
