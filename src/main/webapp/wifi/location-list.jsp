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
<c:if test="${not empty msg}">
    <script type="text/javascript">
        alert('${msg}');
    </script>
</c:if>

<h1>등록된 위치 정보 히스토리</h1>
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
        <tbody>
        <c:forEach var="location" items="${locations}">
            <tr>
                <td>${location.lcId}</td>
                <td>${location.lcLnt}</td>
                <td>${location.lcLat}</td>
                <td>${location.lcDate}</td>
                <td>
                    <form action="/location" method="post">
                        <input name="lcId" value="${location.lcId}" type="hidden"/>
                        <button class="cell-button" type="submit" name="action" value="delete">삭제</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
