# 사용자 위치 기반 서울시 공공 와이파이 정보 조회 프로그램
**목적**
- 사용자의 위치를 기준으로 가장 가까운 와이파이 정보를 20개 가져온다.
- 북마크 기능으로 장소에 따른 인근 와이파이를 저장할 수 있다.

<br>

**주요 기능**
- 와이파이 정보 저장
  - 자바 프로젝트에 OpenAPI 연결
  - 라이브러리를 통해 Json타입 데이터 역직렬화
  - 자바 프로젝트에 데이터베이스 연결
- 사용자 위치 정보 저장 및 가까운 와이파이 정보 조회
  - GeoLocation으로 사용자 위치 정보 가져오기
  - 와이파이 위치, 사용자 위치 간 거리 계산
- 북마크 구현
  - 테이블 조인

<br>

- **Web Layer**
  - Servlet + JSP
- **Persistence Layer**
  - JDBC

<br>

## Development Environment
- **Java**
  - Platform: JakartaEE 8
  - Template: Web Application
  - Version: JDK 1.8
- **IDE**: IntelliJ Ultimate
- **DataBase**: SQLite
- **Server**: apache-tomcat-8.5.99
- **Build**: Gradle

<br>

## 시연 영상
| 기능 | 영상 |
|:-:|:-:|
| 와이파이 정보 저장 |![와이파이 데이터 저장](https://github.com/user-attachments/assets/8294e688-3abe-4ebd-80a9-152dff8eeb88)|
| 사용자 위치 저장 및 근처 와이파이 정보 출력 |![사용자 위치 저장 및 와이파이 정보 출력](https://github.com/user-attachments/assets/68f3d8d1-4bb5-433f-ae13-99f02d3da354)|
| 사용자 위치 저장 내역 출력 및 삭제 |![사용자 위치 정보 등록 내역 및 삭제](https://github.com/user-attachments/assets/b6d4ea65-bc0c-4a60-a02a-e5fa04524574)|
| 와이파이 상세 정보 출력 및 북마크 그룹 추가, 수정, 삭제, 출력 |![와이파이 정보 상세보기 및 북마크 그룹](https://github.com/user-attachments/assets/75bd7bf3-653d-49cc-b899-eceee7c56ffa)|
| 북마크 추가, 삭제, 출력 |![북마크](https://github.com/user-attachments/assets/7469b067-754b-47a6-8161-711505ecf968)|
