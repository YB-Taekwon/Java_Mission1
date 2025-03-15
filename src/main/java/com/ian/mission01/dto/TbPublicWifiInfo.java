package com.ian.mission01.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class TbPublicWifiInfo {
    private int list_total_count;
    private Result RESULT;
    private ArrayList<Row> row;
}
