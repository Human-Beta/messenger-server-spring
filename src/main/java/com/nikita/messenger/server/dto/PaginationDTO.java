package com.nikita.messenger.server.dto;


import javax.validation.constraints.Min;

public class PaginationDTO {
    @Min(1)
    private int page = 1;
    @Min(1)
    private int size = 10;

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }
}
