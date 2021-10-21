-- 기존 데이터베이스가 존재하면 삭제
DROP DATABASE IF EXISTS demodb;

-- 데이터베이스 새로 생성
CREATE DATABASE demodb
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

DROP USER 'demo'@'localhost';
DROP USER 'demo'@'%';
DROP USER 'demo'@'127.0.0.1';
DROP USER 'demo'@'::1';

-- 로컬에서만 접속할 수 있는 사용자를 만들기:
CREATE USER 'demo'@localhost IDENTIFIED BY '1111';
-- 원격에서만 접속할 수 있는 사용자를 만들기:
CREATE USER 'demo'@'%' IDENTIFIED BY '1111';

CREATE USER 'demo'@'127.0.0.1' IDENTIFIED BY '1111';
CREATE USER 'demo'@'::1' IDENTIFIED BY '1111';

-- 유저에게 데이터베이스 권한 부여
GRANT ALL ON demodb.* TO demo@'localhost';
GRANT ALL ON demodb.* TO demo@'%';
GRANT ALL ON demodb.* TO demo@'127.0.0.1';
GRANT ALL ON demodb.* TO demo@'::1';

-- 데이터베이스 접속
use demodb;

-- 회원
DROP TABLE IF EXISTS members RESTRICT;
DROP TABLE IF EXISTS urls RESTRICT;

-- 회원
CREATE TABLE members (
  id             INTEGER      NOT NULL COMMENT '회원번호', -- 회원번호
  email          VARCHAR(50)  NOT NULL COMMENT '이메일', -- 이메일
  pwd            VARCHAR(255) NOT NULL COMMENT '비밀번호', -- 비밀번호
  name           VARCHAR(30)  NOT NULL COMMENT '이름', -- 이름
  create_date    DATETIME     NOT NULL DEFAULT now() COMMENT '가입일', -- 가입일
  authKey        VARCHAR(20)  NULL     COMMENT '이메일인증', -- 이메일인증
  provider		 VARCHAR(20)  NULL	   COMMENT '정보제공자' -- 정보제공자
)
COMMENT '회원';

-- 회원
ALTER TABLE members
  ADD CONSTRAINT PK_members -- 회원 기본키
    PRIMARY KEY (
      id -- 회원번호
    );

ALTER TABLE members
  MODIFY COLUMN id INTEGER NOT NULL AUTO_INCREMENT COMMENT '회원번호';

-- url
CREATE TABLE urls (
  id            INTEGER      NOT NULL COMMENT '등록번호',
  member_id     INTEGER      NOT NULL COMMENT '회원번호',
  name          VARCHAR(100) NOT NULL COMMENT '이름',
  url           TEXT         NOT NULL COMMENT 'url',
  description   TEXT         NULL     COMMENT '설명',
  expirationDate DATETIME    NULL     COMMENT '만료일',
  isPublic      INTEGER      NOT NULL DEFAULT 1 COMMENT '공개설정'
)
COMMENT 'URL';

-- url
ALTER TABLE urls
  ADD CONSTRAINT PK_urls -- URL 기본키
    PRIMARY KEY (
      id -- 등록번호
    );

ALTER TABLE urls
  MODIFY COLUMN id INTEGER NOT NULL AUTO_INCREMENT COMMENT '등록번호';
