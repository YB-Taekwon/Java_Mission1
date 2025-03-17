package com.ian.mission01.servlet;

import com.google.gson.Gson;
import com.ian.mission01.repository.WiFiRepository;
import com.ian.mission01.dto.Row;
import com.ian.mission01.dto.WiFiResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;


@WebServlet("/load-wifi")
public class OpenAPIServlet extends HttpServlet {

    private static final String API_URL = "http://openapi.seoul.go.kr:8088/4258714f4b6a787832346863627a6a/json/TbPublicWifiInfo/%d/%d/";
    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();
    private static WiFiRepository wiFiRepository;


    @Override
    public void init() {
        wiFiRepository = WiFiRepository.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 모든 데이터 저장을 위한 배치 처리
        int totalItems = getTotalCount();
        int batchSize = 1000;
        int totalBatches = (totalItems + batchSize - 1) / batchSize;

        for (int i = 0; i < totalBatches; i++) {

            int start = i * batchSize;
            int end = Math.min((i + 1) * batchSize, totalItems);

            String url = String.format(API_URL, start, end);
            Request wifiRequest = new Request.Builder().url(url).build();

            try (Response wifiResponse = client.newCall(wifiRequest).execute()) {

                if (!wifiResponse.isSuccessful()) throw new IOException("Unexpected code " + wifiResponse);

                String responseBody = wifiResponse.body().string();
                WiFiResponse wifiData = gson.fromJson(responseBody, WiFiResponse.class);

                Optional<ArrayList<Row>> rowsOptional = Optional.ofNullable(wifiData.getTbPublicWifiInfo())
                        .flatMap(tbPublicWifiInfo -> Optional.ofNullable(tbPublicWifiInfo.getRow()));
                rowsOptional.ifPresent(rows -> {
                    try {
                        wiFiRepository.insertWifi(rows);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        req.setAttribute("totalItems", totalItems);
        req.getRequestDispatcher("/wifi/load-wifi.jsp").forward(req, resp);
    }


    // OpenAPI의 첫 번째 데이터에서 전체 데이터 개수를 가져옴
    private int getTotalCount() {
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
