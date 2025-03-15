package com.ian.mission01.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Location {
    private int lcId; // ID
    private double lcLat; // Y좌표
    private double lcLnt; // X좌표
    private LocalDate lcDate; // 조회일자


    public Location() {
    }
}
