package com.nikita.messenger.server.model;

public class User {

    private long id;
    private String nickName;
    private String password;
    private String name;
    private String avatarUrl;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(final String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(final String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof User) {
            final User aUser = (User) obj;

            return id == aUser.id;
        }

        return false;
    }
}
