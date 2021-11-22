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


DROP TABLE IF EXISTS members RESTRICT;
DROP TABLE IF EXISTS urls RESTRICT;
DROP TABLE IF EXISTS products RESTRICT;
DROP TABLE IF EXISTS payments RESTRICT;
DROP TABLE IF EXISTS orders RESTRICT;


-- 회원
CREATE TABLE members (
  id             INTEGER      NOT NULL COMMENT '회원번호', -- 회원번호
  email          VARCHAR(50)  NOT NULL COMMENT '이메일', -- 이메일
  pwd            VARCHAR(255) NOT NULL COMMENT '비밀번호', -- 비밀번호
  name           VARCHAR(30)  NOT NULL COMMENT '이름', -- 이름
  create_date    DATETIME     NOT NULL DEFAULT now() COMMENT '가입일', -- 가입일
  auth_key        VARCHAR(50)  NULL     COMMENT '이메일인증', -- 이메일인증
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
  expiration_date DATETIME    NULL     COMMENT '만료일',
  is_public      INTEGER      NOT NULL DEFAULT 1 COMMENT '공개설정'
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


-- 상품
CREATE TABLE products (
  id            INTEGER      NOT NULL COMMENT '등록번호',
  name          VARCHAR(100) NOT NULL COMMENT '이름',
  price         INTEGER      NOT NULL COMMENT '가격'
)
COMMENT '상품';

-- 상품
ALTER TABLE products
  ADD CONSTRAINT PK_products -- products 기본키
    PRIMARY KEY (
      id -- 등록번호
    );

ALTER TABLE products
  MODIFY COLUMN id INTEGER NOT NULL AUTO_INCREMENT COMMENT '등록번호';


-- 결제
CREATE TABLE payments (
  id            VARCHAR(30)  NOT NULL COMMENT '등록번호',
  member_id     INTEGER      NOT NULL COMMENT '회원번호',
  product_id    INTEGER      NOT NULL COMMENT '상품번호',
  pay_method    VARCHAR(100) NULL     COMMENT '결제수단',
  amount        INTEGER      NULL     COMMENT '금액',
  quota         INTEGER      NULL     COMMENT '할부개월수',
  card_num      VARCHAR(20)  NULL     COMMENT '카드번호',
  card_name     VARCHAR(30)  NULL     COMMENT '카드이름',
  paid_at       INTEGER      NULL     COMMENT '결제시간',
  imp_uid       VARCHAR(50)  NULL     COMMENT '아임포트고유결제번호'
)
COMMENT '결제';

-- 결제
ALTER TABLE payments
  ADD CONSTRAINT PK_payments -- payments 기본키
    PRIMARY KEY (
      id -- 등록번호
    );


-- 주문
CREATE TABLE orders (
  id            INTEGER      NOT NULL COMMENT '등록번호',
  member_id     INTEGER      NOT NULL COMMENT '회원번호',
  product_id    INTEGER      NOT NULL COMMENT '상품번호',
  start_date    DATETIME     NOT NULL COMMENT '시작일',
  end_date      DATETIME     NULL     COMMENT '마감일'
)
COMMENT '주문';

-- 주문
ALTER TABLE orders
  ADD CONSTRAINT PK_orders -- orders 기본키
    PRIMARY KEY (
      id -- 등록번호
    );

ALTER TABLE orders
  MODIFY COLUMN id INTEGER NOT NULL AUTO_INCREMENT COMMENT '등록번호';