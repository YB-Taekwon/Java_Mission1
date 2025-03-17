<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Public WiFi Information</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script type="text/javascript" src="js/script.js"></script>
</head>
<body>
<h1>근처 와이파이 정보 조회</h1>
<div>
    <a href="/">홈</a>
    <span>|</span>
    <a href="/location">위치 히스토리 목록</a>
    <span>|</span>
    <a href="/load-wifi">Open API 와이파이 정보 가져오기</a>
    <span>|</span>
    <a href="/bookmark">북마크 조회</a>
    <span>|</span>
    <a href="/bookmarkGroup">북마크 그룹 관리</a>
</div>

<div class="input-container">
    <form action="/wifi" method="post">
        <label for="lat" class="input-label">LAT:</label>
        <input id="lat" class="input-box" name="lat" type="text" value="${location.lcLat}" readonly/>
        <label for="lnt" class="input-label">LNT:</label>
        <input id="lnt" class="input-box" name="lnt" type="text" value="${location.lcLnt}" readonly/>
        <input name="action" type="hidden" value="save"/>
        <button class="cell-button" type="button" onclick="getLocation()">내 위치 가져오기</button>
        <button class="cell-button" id="submitButton" type="submit" disabled>근처 WiFi 정보 가져오기</button>
    </form>
</div>

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
        <tbody>
        <c:forEach var="row" items="${rows}">
            <tr>
                <td>${row.distance}</td>
                <td>${row.wfId}</td>
                <td>${row.wfArea}</td>
                <c:url var="encodeURL" value="/wifi">
                    <c:param name="wfId" value="${row.wfId}"/>
                    <c:param name="action" value="info"/>
                </c:url>
                <td><a href="${encodeURL}">${row.wfName}</a></td>
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
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
