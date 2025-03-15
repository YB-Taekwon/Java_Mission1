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

        function sendData() {

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

<div class="table-container">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>조회일자</th>
            <th>비고</th>
        </tr>
        </thead>
        <c:forEach var="location" items="${locations}">
            <tbody>
            <tr>
                <td>${location.lcId}</td>
                <td>${location.lcLnt}</td>
                <td>${location.lcLat}</td>
                <td>${location.lcDate}</td>
                <td>
                    <button type="button" onclick="sendData(${location.lcId})">삭제</button>
                </td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
</div>

</body>
</html>
