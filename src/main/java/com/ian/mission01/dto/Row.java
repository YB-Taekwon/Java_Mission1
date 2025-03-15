package com.ian.mission01.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Row {
    @SerializedName("X_SWIFI_MGR_NO")
    private String wfId;  // 관리번호
    @SerializedName("X_SWIFI_WRDOFC")
    private String wfArea;  // 자치구
    @SerializedName("X_SWIFI_MAIN_NM")
    private String wfName; // 와이파이명
    @SerializedName("X_SWIFI_ADRES1")
    private String wfAddress1;  // 도로명주소
    @SerializedName("X_SWIFI_ADRES2")
    private String wfAddress2;  // 상세주소
    @SerializedName("X_SWIFI_INSTL_FLOOR")
    private String wfFloor; // 설치위치(층)
    @SerializedName("X_SWIFI_INSTL_TY")
    private String wfType;    // 설치유형
    @SerializedName("X_SWIFI_INSTL_MBY")
    private String wfProvider;   // 설치기관
    @SerializedName("X_SWIFI_SVC_SE")
    private String wfService;  // 서비스구분
    @SerializedName("X_SWIFI_CMCWR")
    private String wfNetwork;   // 망종류
    @SerializedName("X_SWIFI_CNSTC_YEAR")
    private String wfYear;  // 설치년도
    @SerializedName("X_SWIFI_INOUT_DOOR")
    private String wfInOut;  // 실내외구분
    @SerializedName("X_SWIFI_REMARS3")
    private String wfCondition; // wifi접속환경
    @SerializedName("LAT")
    private String wfLat; // Y좌표
    @SerializedName("LNT")
    private String wfLnt; // X좌표
    @SerializedName("WORK_DTTM")
    private String wfDate;    // 작업일자
    private double distance; // 거리


    public Row() {
    }

    public Row(String wfName) {
        this.wfName = wfName;
    }

    public Row(String wfLnt, String wfLat) {
        this.wfLnt = wfLnt;
        this.wfLat = wfLat;
    }
}
