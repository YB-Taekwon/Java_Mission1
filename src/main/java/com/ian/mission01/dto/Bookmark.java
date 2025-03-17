package com.ian.mission01.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Bookmark {
    private int bmId;
    private BookmarkGroup bookmarkGroup;
    private Row row;
    private LocalDateTime bmDate;

    public Bookmark() {

    }

    public Bookmark(int bmId, BookmarkGroup bookmarkGroup, Row row, LocalDateTime bmDate) {
        this.bmId = bmId;
        this.bookmarkGroup = bookmarkGroup;
        this.row = row;
        this.bmDate = bmDate;
    }
}
