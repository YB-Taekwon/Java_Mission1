package com.ian.mission01;


import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

public class OpenAPIClient {
    private static final String API_URL = "http://openapi.seoul.go.kr:8088/4258714f4b6a787832346863627a6a/json/TbPublicWifiInfo/%d/%d/";
    private static final int BATCH_SIZE = 1000;

    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();

    public static void main(String[] args) throws Exception {

        int totalItems = getTotalCount();
        int totalBatches = (totalItems + BATCH_SIZE - 1) / BATCH_SIZE;

        System.out.println(totalItems);

        for (int i = 0; i < totalBatches; i++) {
            int start = i * BATCH_SIZE + 1;
            int end = Math.min((i + 1) * BATCH_SIZE, totalItems);
            String url = String.format(API_URL, start, end);

            Request wifiRequest = new Request.Builder().url(url).build();
            try (Response wifiResponse = client.newCall(wifiRequest).execute()) {
                if (!wifiResponse.isSuccessful()) throw new IOException("Unexpected code " + wifiResponse);

                String responseBody = wifiResponse.body().string();
                Gson gson = new Gson();
                WiFiResponse wifiData = gson.fromJson(responseBody, WiFiResponse.class);

                ArrayList<Row> rows = wifiData.getTbPublicWifiInfo().getRow();
                for (Row row : rows) {
                    System.out.println(row.getX_SWIFI_MAIN_NM());
                }
            }
        }

    }

    private static int getTotalCount() {
        String url = "http://openapi.seoul.go.kr:8088/4258714f4b6a787832346863627a6a/json/TbPublicWifiInfo/1/1/";
        Request wifiRequest = new Request.Builder().url(url).build();

        try (Response wifiResponse = client.newCall(wifiRequest).execute()) {
            if (!wifiResponse.isSuccessful()) throw new IOException("Unexpected code " + wifiResponse);
            String responseBody = wifiResponse.body().string();
            WiFiResponse wifiData = gson.fromJson(responseBody, WiFiResponse.class);
            return wifiData.getTbPublicWifiInfo().getList_total_count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
