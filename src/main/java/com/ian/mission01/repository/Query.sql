CREATE TABLE WIFI
(
    W_ID        VARCHAR(15) PRIMARY KEY, -- 관리번호
    W_AREA      VARCHAR(10),             -- 자치구
    W_NAME      VARCHAR(20),             -- 와이파이명
    W_ADDRESS1  VARCHAR(30),             -- 도로명주소
    W_ADDRESS2  VARCHAR(30),             -- 상세주소
    W_FLOOR     VARCHAR(30),             -- 설치위치(층)
    W_TYPE      VARCHAR(30),             -- 설치유형
    W_PROVIDER  VARCHAR(30),             -- 설치기관
    W_SERVICE   VARCHAR(30),             -- 서비스구분
    W_NETWORK   VARCHAR(30),             -- 망종류
    W_YEAR      VARCHAR(4),              -- 설치년도
    W_INOUT     VARCHAR(30),             -- 실내외구분
    W_CONDITION VARCHAR(30),             -- wifi접속환경
    W_LAT       DOUBLE,                  -- Y좌표
    W_LNT       DOUBLE,                  -- X좌표
    W_DATE      DATETIME                 -- 작업일자
);


CREATE TABLE LOCATION
(
    L_ID   INTEGER PRIMARY KEY AUTOINCREMENT, -- 위치정보 아이디
    L_LAT  DOUBLE NOT NULL,                   -- 위치정보 Y좌표
    L_LNT  DOUBLE NOT NULL,                   -- 위치정보 X좌표
    L_DATE TEXT DEFAULT (datetime('now', 'localtime'))
);


CREATE TABLE BOOKMARK_GROUP
(
    BG_ID          INTEGER PRIMARY KEY AUTOINCREMENT,           -- 북마크그룹 아이디
    BG_NAME        VARCHAR(15) UNIQUE NOT NULL,                 -- 북마크 그룹명
    BG_ORDER       INTEGER            NOT NULL,                 -- 북마크그룹 순서
    BG_REGIST_DATE TEXT DEFAULT (datetime('now', 'localtime')), -- 북마크그룹 등록일자
    BG_UPDATE_DATE TEXT                                         -- 북마크그룹 수정일자
);


CREATE TABLE BOOKMARK
(
    B_ID           INTEGER PRIMARY KEY AUTOINCREMENT,           -- 북마크 아이디
    BG_NAME        VARCHAR(15) NOT NULL,                        -- 북마크 그룹명
    W_NAME         VARCHAR(20) NOT NULL,                        -- 와이파이명
    B_DATE TEXT DEFAULT (datetime('now', 'localtime')), -- 북마크 등록일자
    FOREIGN KEY (BG_NAME) REFERENCES BOOKMARK_GROUP (BG_NAME),
    FOREIGN KEY (W_NAME) REFERENCES WIFI (W_NAME)
);