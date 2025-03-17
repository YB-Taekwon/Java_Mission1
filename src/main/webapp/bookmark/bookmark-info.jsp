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
<h1>북마크 상세 정보</h1>
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
    <form action="/bookmark" method="POST">
        <table>
            <tr>
                <th>북마크명</th>
                <td colspan="17">${bookmark.bookmarkGroup.bmgName}</td>
            </tr>
            <tr>
                <th>관리번호</th>
                <td colspan="17">${bookmark.row.wfName}</td>
            </tr>

            <tr>
                <th>등록일자</th>
                <td colspan="17">${bookmark.bmDate}</td>
            </tr>
            <tr>
                <td colspan="2">
                    <input name="bmId" value="${bookmark.bmId}" type="hidden"/>
                    <button class="cell-button" type="submit" name="action" value="delete">삭제</button>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
