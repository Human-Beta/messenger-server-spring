package com.nikita.messenger.server.dto;

public abstract class AbstractDTO {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }
}
