package com.ian.mission01.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookmarkGroup {
    private int bmgId; // 북마크그룹 아이디
    private String bmgName; // 북마크그룹 이름
    private int bmgOrder; // 북마크그룹 순서
    private LocalDateTime bmgRegistDate; // 북마크그룹 등록일자
    private LocalDateTime bmgUpdateDate; // 북마크그룹 수정일자
    private String bmgUpdateDateString;

    public BookmarkGroup() {
    }

    public BookmarkGroup(String bmgName) {
        this.bmgName = bmgName;
    }
}