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
<h1>북마크 그룹 추가</h1>
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
    <form action="/bookmarkGroup" method="post">
        <table>
            <thead>
            <tr>
                <th>북마크 이름</th>
                <td>
                    <label for="bmgName"></label>
                    <input id="bmgName" name="bmgName" type="text"/>
                </td>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th>순서</th>
                <td>
                    <label for="bmgOrder"></label>
                    <input id="bmgOrder" name="bmgOrder" type="text">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <button class="cell-button" type="submit" name="action" value="add">추가</button>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>


</body>
</html>
