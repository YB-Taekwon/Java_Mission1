<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
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
    </script>
</head>
<body>
<h1>와이파이 정보 구하기</h1>
<br/>
<div>
    <a href="/">홈</a>
    <span>|</span>
    <a href="#">위치 히스토리 목록</a>
    <span>|</span>
    <a href="load-wifi">Open API 와이파이 정보 가져오기</a>
</div>
<div>
    <label for="lat">LAT:</label>
    <input id="lat" type="text"/>
    <label for="lnt">LNT:</label>
    <input id="lnt" type="text"/>
    <button onclick="getLocation()">내 위치 가져오기</button>
</div>
</body>
</html>