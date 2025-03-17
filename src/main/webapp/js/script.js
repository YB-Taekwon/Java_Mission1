// GeoLocation API
function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition, showError);
    } else {
        alert("브라우저에서 API를 지원하지 않습니다.");
    }
}

// 좌표를 받아오는 함수
function showPosition(position) {
    // 받아온 좌표를 변수에 저장
    const lat = position.coords.latitude;
    const lnt = position.coords.longitude;

    // 좌표를 value 값으로 설정
    document.getElementById("lat").value = lat;
    document.getElementById("lnt").value = lnt;

    // 값을 받아오면 disabled 해제
    document.getElementById("submitButton").disabled = false;
}

// 좌표를 받아오지 못 했을 때 에러 알림 호출
function showError(error) {
    switch (error.code) {
        case error.PERMISSION_DENIED:
            alert("사용자가 위치 정보 접근을 허용하지 않습니다.");
            break;
        case error.POSITION_UNAVAILABLE:
            alert("위치 정보가 불가능하거나 지원되지 않습니다.");
            break;
        case error.TIMEOUT:
            alert("위치 정보 요청이 시간이 초과되었습니다.");
            break;
        case error.UNKNOWN_ERROR:
            alert("기타 알 수 없는 에러가 발생했습니다.");
            break;
    }
}