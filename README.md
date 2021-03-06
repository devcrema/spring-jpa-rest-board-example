# spring-jpa-rest-board-example

spring jpa rest board example, toy project

재미삼아 만들어본 스프링 부트 + JPA + rest 게시판 토이 프로젝트

### 이번 프로젝트에서 써본 것들

|써본 것들|comment|
|---|-------|
|Spring boot | 스프링의 무의미한 설정을 확 줄여줌|
|Spring data jpa | 데이터를 쉽게 조작할 수 있도록 해줌|
|Spring security | 유저 인증 부분에 쓰임|
|Spring boot test | 테스트시에는 인메모리 db(h2)쓰도록 설정해둠.|
|lombok | 무의미하게 쓰게되는 실제 코드 라인수가 몇배까지 절약됨|
|apache.commons:commons-lang3 | StringUtils.isBlank(string);이런 몇가지 너무 자주 쓰는 유틸을 모아놓아서 편함|
|model mapper | DTO -> entity 쉽게 변환가능|
|gradle |  |
|springfox-Swagger2 | api 문서화 용도. (release가 안올라옴에 따라 제거)|
|spring-security-oauth2 | deprecated|

### TODO

| TODO        | done? | comment |
|-------------|:-----:|---------|
| 유저 구현     | O     |   spring security (UserDetails, UserDetailsService) 로 구현함.      |
| 유저 가입     | O      | 완료 |
| 유저 로그인    | O      | JWT test 완료 |
| 게시글 리스트  | O  |   |
| 게시글 작성   | O |  |
| 게시글 조회   | O |  |
| 게시글 수정   | O |  |
| migrate to springBootTest2.2 junit 5 | O | testConstructor 설정, junit5 |
| spring rest docs | | |
| queryDSL로 통계 구현 | | |
| mysql 8.0 migration |O| |
| spring cache를 이용한 게시글 캐시 설정| | |
| redis로 조회수 랭킹 설정| | |
| spring batch를 이용한 유저 설정| | |

## with docker
1. install docker
2. build project
3. docker-compose -f board/docker-compose.yml up

## 소스 설정 방법
1. mysql 8.0 설치 및 아래 sql 알맞게 실행
2. 프로젝트 import
3. IDE 에 lombok 설정 (IntelliJ 쓸 경우, 어노테이션 설정 포함)

#### mysql 8.0

create database jpa_rest_board_example DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci; #디비 생성

create user 'jpa_rest_board_example_admin'@'localhost' identified by 'jpa_rest_board_example_pw'; #유저 생성

GRANT ALL PRIVILEGES ON jpa_rest_board_example.* TO 'jpa_rest_board_example_admin'@'localhost' identified by 'jpa_rest_board_example_pw';#권한 설정
