# spring-jpa-rest-board-example
spring jpa rest board example, toy project

재미삼아 만들어본 스프링 부트 + JPA + rest 게시판 토이 프로젝트

###mysql 5.7 
create database jpa_rest_board_example DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci; #디비 생성


create user 'jpa_rest_board_example_admin'@'localhost' identified by 'jpa_rest_board_example_pw'; #유저 생성


GRANT ALL PRIVILEGES ON jpa_rest_board_example.* TO 'jpa_rest_board_example_admin'@'localhost' identified by 'jpa_rest_board_example_pw';#권한 설정