package com.ian.mission01;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static com.ian.mission01.WiFiRepository.*;

@WebServlet(urlPatterns = {"/load-wifi"})
public class WiFiServlet extends HttpServlet {

    private static final String API_URL = "http://openapi.seoul.go.kr:8088/4258714f4b6a787832346863627a6a/json/TbPublicWifiInfo/%d/%d/";
    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();

    // Service
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getServletPath();

        // URL이 /load-wifi 일 때
        if ("/load-wifi".equals(path)) {
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
                        processData(rows);
                    });
                }
            }
        }
    }


    // Repository의 insert 메서드를 호출하여 데이터를 DB에 저장
    private void processData(ArrayList<Row> rows) {
        try (Connection conn = connect()) {
            insert(conn, rows);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
