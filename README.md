# spring-jpa-rest-board-example

spring jpa rest board example, toy project

재미삼아 만들어본 스프링 부트 + JPA + rest 게시판 토이 프로젝트


Gradle 모듈
- api : api 게시판 서버 (controller + config)
- core : core 모듈 (service + entity)

spring-boot-starter-security + spring-security-oauth2

### 소스 설정 방법
1. mysql 5.7 설치 및 아래 sql 알맞게 실행
2. IDE 에 lombok 설정 (intellij 쓸 경우, 어노테이션 설정 포함)
3. 프로젝트 import

### mysql 5.7 

create database jpa_rest_board_example DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci; #디비 생성

create user 'jpa_rest_board_example_admin'@'localhost' identified by 'jpa_rest_board_example_pw'; #유저 생성

GRANT ALL PRIVILEGES ON jpa_rest_board_example.* TO 'jpa_rest_board_example_admin'@'localhost' identified by 'jpa_rest_board_example_pw';#권한 설정