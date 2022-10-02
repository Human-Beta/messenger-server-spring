package com.nikita.messenger.server.dto;

import javax.validation.constraints.Min;

public class PaginationDTO {
    @Min(value = 1)
    private int page = 1;
    @Min(value = 1)
    private int size = 50;

    public PaginationDTO() {
    }

    public PaginationDTO(final int page, final int size) {
        this.page = page;
        this.size = size;
    }

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
