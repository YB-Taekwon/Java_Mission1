<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<h1>북마크 조회</h1>
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
            <th>북마크 그룹</th>
            <th>와이파이명</th>
            <th>등록일자</th>
            <th>비고</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="bookmark" items="${bookmarks}">
            <tr>
                <td>${bookmark.bmId}</td>
                <td>${bookmark.bookmarkGroup.bmgName}</td>
                <td>${bookmark.row.wfName}</td>
                <td>${bookmark.bmDate}</td>
                <td>
                    <form action="/bookmark" method="get">
                        <input id="bmId" name="bmId" type="hidden" value="${bookmark.bmId}"/>
                        <button class="cell-button" type="submit" name="action" value="info">정보</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
