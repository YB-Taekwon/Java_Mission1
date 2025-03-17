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

<c:if test="${not empty msg}">
    <script type="text/javascript">
        alert('${msg}');
    </script>
</c:if>

<h1>북마크 그룹 관리</h1>
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

<a class="cell-button" href="/bookmarkGroup/add-bookmarkgroup.jsp">북마크 그룹 추가</a>

<div class="table-container">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>북마크명</th>
            <th>순서</th>
            <th>등록일자</th>
            <th>수정일자</th>
            <th>비고</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="bookmarkGroup" items="${bookmarkGroups}">
            <tr>
                <td>${bookmarkGroup.bmgId}</td>
                <td>${bookmarkGroup.bmgName}</td>
                <td>${bookmarkGroup.bmgOrder}</td>
                <td>${bookmarkGroup.bmgRegistDate}</td>
                <td>${bookmarkGroup.bmgUpdateDateString}</td>
                <td>
                    <form action="/bookmarkGroup" method="get">
                        <input id="bmgId" name="bmgId" type="hidden" value="${bookmarkGroup.bmgId}"/>
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
