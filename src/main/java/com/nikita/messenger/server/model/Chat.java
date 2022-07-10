package com.nikita.messenger.server.model;


import com.nikita.messenger.server.enums.ChatType;

import java.util.List;

public class Chat {
    private long id;
    private ChatType type;
//    TODO: lazy load users
    private List<User> users;

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
}
