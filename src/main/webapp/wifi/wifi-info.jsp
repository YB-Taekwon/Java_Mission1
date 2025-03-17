<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Public WiFi Information</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<h1>근처 와이파이 상세 정보</h1>
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

<div class="dropdown-container">
    <form action="/bookmark" method="post">
        <label for="dropdown" class="dropdown-label"></label>
        <select id="dropdown" class="dropdown" name="bmgName">
            <option value="" disabled selected>북마크 그룹 선택</option>
            <c:forEach var="bookmarkGroup" items="${bookmarkGroups}">
                <option value="${bookmarkGroup.bmgName}">${bookmarkGroup.bmgName}</option>
            </c:forEach>
        </select>
        <input name="wfName" value="${row.wfName}" type="hidden"/>
        <button class="cell-button" name="action" type="submit" value="add">추가</button>
    </form>
</div>

<div class="table-container">
    <table>
        <tr>
            <th>거리(Km)</th>
            <td>${row.distance}</td>
        </tr>
        <tr>
            <th>관리번호</th>
            <td>${row.wfId}</td>
        </tr>
        <tr>
            <th>자치구</th>
            <td>${row.wfArea}</td>
        </tr>
        <tr>
            <th>와이파이명</th>
            <td>${row.wfName}</td>
        </tr>
        <tr>
            <th>도로명주소</th>
            <td>${row.wfAddress1}</td>
        </tr>
        <tr>
            <th>상세주소</th>
            <td>${row.wfAddress2}</td>
        </tr>
        <tr>
            <th>설치위치(층)</th>
            <td>${row.wfFloor}</td>
        </tr>
        <tr>
            <th>설치유형</th>
            <td>${row.wfType}</td>
        </tr>
        <tr>
            <th>설치기관</th>
            <td>${row.wfProvider}</td>
        </tr>
        <tr>
            <th>서비스구분</th>
            <td>${row.wfService}</td>
        </tr>
        <tr>
            <th>망종류</th>
            <td>${row.wfNetwork}</td>
        </tr>
        <tr>
            <th>설치년도</th>
            <td>${row.wfYear}</td>
        </tr>
        <tr>
            <th>실내외구분</th>
            <td>${row.wfInOut}</td>
        </tr>
        <tr>
            <th>wifi접속환경</th>
            <td>${row.wfCondition}</td>
        </tr>
        <tr>
            <th>Y좌표</th>
            <td>${row.wfLat}</td>
        </tr>
        <tr>
            <th>X좌표</th>
            <td>${row.wfLnt}</td>
        </tr>
        <tr>
            <th>작업일자</th>
            <td>${row.wfDate}</td>
        </tr>
    </table>
</div>
</body>
</html>
