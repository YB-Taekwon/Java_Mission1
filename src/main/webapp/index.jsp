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
<h1>사용자 위치 기반 공공 와이파이 정보 조회</h1>
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
        <input id="lat" class="input-box" name="lat" type="text" value="0.0" readonly/>
        <label for="lnt" class="input-label">LNT:</label>
        <input id="lnt" class="input-box" name="lnt" type="text" value="0.0" readonly/>
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
        <tr>
            <td colspan="17">현재 위치를 입력한 후에 조회해주세요.</td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>