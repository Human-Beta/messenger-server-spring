package com.nikita.messenger.server.data;

import java.util.List;

public class PageData<R> {
    private List<R> content;
    private long totalPages;

    public PageData(final List<R> content, final long totalPages) {
        this.content = content;
        this.totalPages = totalPages;
    }

    public List<R> getContent() {
        return content;
    }

    public void setContent(final List<R> content) {
        this.content = content;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final long totalPages) {
        this.totalPages = totalPages;
    }
}
