package com.nikita.messenger.server.model;


import com.nikita.messenger.server.enums.ChatType;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type_id")
    private ChatType type;
    @ManyToMany
    @JoinTable(
            name = "chats_users",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @ManyToOne
    @JoinFormula("(SELECT m.id FROM messages m WHERE m.chat_id=id ORDER BY m.date DESC LIMIT 1)")
    private Message lastMessage;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public ChatType getType() {
        return type;
    }

    public void setType(final ChatType type) {
        this.type = type;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(final Message lastMessage) {
        this.lastMessage = lastMessage;
    }
}
