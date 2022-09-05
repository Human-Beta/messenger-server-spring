package com.nikita.messenger.server.dto;

import java.util.List;

public class PageDTO<R> {
    private List<R> values;
    private long totalPages;

    public PageDTO(final List<R> values, final long totalPages) {
        this.values = values;
        this.totalPages = totalPages;
    }

    public List<R> getValues() {
        return values;
    }

    public void setValues(final List<R> values) {
        this.values = values;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final long totalPages) {
        this.totalPages = totalPages;
    }
}
