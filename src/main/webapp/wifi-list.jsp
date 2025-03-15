<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/style.css">

    <script>
        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition, showError);
            } else {
                alert("Geolocation is not supported by this browser.");
            }
        }

        function showPosition(position) {
            const lat = position.coords.latitude;
            const lnt = position.coords.longitude;

            document.getElementById("lat").value = lat;
            document.getElementById("lnt").value = lnt;

            document.getElementById("submitButton").disabled = false;
        }

        function showError(error) {
            switch (error.code) {
                case error.PERMISSION_DENIED:
                    alert("User denied the request for Geolocation.");
                    break;
                case error.POSITION_UNAVAILABLE:
                    alert("Location information is unavailable.");
                    break;
                case error.TIMEOUT:
                    alert("The request to get user location timed out.");
                    break;
                case error.UNKNOWN_ERROR:
                    alert("An unknown error occurred.");
                    break;
            }
        }
    </script>
</head>
<body>
<nav>
    <a href="/">홈</a>
    <span>|</span>
    <a href="location-history">위치 히스토리 목록</a>
    <span>|</span>
    <a href="load-wifi">Open API 와이파이 정보 가져오기</a>
    <a href="#">즐겨찾기 보기</a>
    <span>|</span>
    <a href="#">즐겨찾기 그룹 관리</a>
</nav>

<form action="/wifi-list" method="get">
    <label for="lat">LAT:</label>
    <input id="lat" name="lat" type="text" value="${location.lcLat}" readonly/>
    <label for="lnt">LNT:</label>
    <input id="lnt" name="lnt" type="text" value="${location.lcLnt}" readonly/>
    <button type="button" onclick="getLocation()">내 위치 가져오기</button>
    <button id="submitButton" type="submit" disabled>근처 WiFi 정보 가져오기</button>
</form>
<div class="table-container">
    <table>
        <thead>
        <tr>
            <th>거리(Km)</th>
            <th>관리번호</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>도로명주소</th>
            <th>상세주소</th>
            <th>설치위치(층)</th>
            <th>설치유형</th>
            <th>설치기관</th>
            <th>서비스구분</th>
            <th>망종류</th>
            <th>설치년도</th>
            <th>실내외구분</th>
            <th>wifi접속환경</th>
            <th>Y좌표</th>
            <th>X좌표</th>
            <th>작업일자</th>
        </tr>
        </thead>
        <c:forEach var="row" items="${rows}">
            <tbody>
            <tr>
                <td>${row.distance}</td>
                <td>${row.wfId}</td>
                <td>${row.wfArea}</td>
                <td><a href="/wifi-info">${row.wfName}</a></td>
                <td>${row.wfAddress1}</td>
                <td>${row.wfAddress2}</td>
                <td>${row.wfFloor}</td>
                <td>${row.wfType}</td>
                <td>${row.wfProvider}</td>
                <td>${row.wfService}</td>
                <td>${row.wfNetwork}</td>
                <td>${row.wfYear}</td>
                <td>${row.wfInOut}</td>
                <td>${row.wfCondition}</td>
                <td>${row.wfLat}</td>
                <td>${row.wfLnt}</td>
                <td>${row.wfDate}</td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
</div>

</body>
</html>
