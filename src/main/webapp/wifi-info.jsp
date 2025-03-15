<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/style.css">

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

<form action="#" method="get">
    <label for="bookmark"></label>
    <select id="bookmark" name="bookmark">
        <option value="#">북마크 선택</option>
        <c:forEach var="bookmarkGroup" items="#">
            <option value="#">#</option>
        </c:forEach>
    </select>
    <button type="submit">북마크 추가</button>
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
        <%--        <c:forEach var="row" items="${rows}">--%>
        <%--            <tbody>--%>
        <%--            <tr>--%>
        <%--                <td>${row.distance}</td>--%>
        <%--                <td>${row.mgrNo}</td>--%>
        <%--                <td>${row.wrdofc}</td>--%>
        <%--                <td><a href="/wifi-info">${row.mainNm}</a></td>--%>
        <%--                <td>${row.adres1}</td>--%>
        <%--                <td>${row.adres2}</td>--%>
        <%--                <td>${row.instlFloor}</td>--%>
        <%--                <td>${row.instlTy}</td>--%>
        <%--                <td>${row.instlMby}</td>--%>
        <%--                <td>${row.svcSe}</td>--%>
        <%--                <td>${row.cmcwr}</td>--%>
        <%--                <td>${row.cnstcYear}</td>--%>
        <%--                <td>${row.inOutDoor}</td>--%>
        <%--                <td>${row.remars}</td>--%>
        <%--                <td>${row.lat}</td>--%>
        <%--                <td>${row.lnt}</td>--%>
        <%--                <td>${row.workDtTm}</td>--%>
        <%--            </tr>--%>
        <%--            </tbody>--%>
        <%--        </c:forEach>--%>
    </table>
</div>

</body>
</html>
