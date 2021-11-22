-- 회원 예제 데이터
INSERT INTO members(id, email, pwd, name, create_date) VALUES (1, 'yh0921k@gmail.com', PASSWORD('1111'), '김용희', '2020-06-04');
INSERT INTO members(email, pwd, name, create_date) VALUES ('moonsolid@naver.com', PASSWORD('2222'), '문국태', '2020-06-05');
INSERT INTO members(email, pwd, name, create_date) VALUES ('oreotaste@daum.net', PASSWORD('3333'), '손영쿡', '2020-06-06');
INSERT INTO members(email, pwd, name) VALUES ('zpion@naver.com', PASSWORD('1111111111'), '윤리아');

-- url 예제 데이터
INSERT INTO urls(id, member_id, name, url, expirationDate) VALUES (1, 1, 'hello', 'https://www.test.com', '2022-06-04');
INSERT INTO urls(member_id, name, url, expirationDate) VALUES (4, 'AAA', 'https://www.aaa.co.kr', '2022-06-05');
INSERT INTO urls(member_id, name, url) VALUES (4, 'BBB', 'https://www.bbb.com');
INSERT INTO urls(member_id, name, url) VALUES (5, 'ccc', 'https://www.ccc.com');

-- 상품 예제 데이터
INSERT INTO products(id, name, price) VALUES (1, 'mybatis+', 10);
INSERT INTO products(name, price) VALUES ('mybatis+ 3month', 30);

-- 결제 예제 데이터
INSERT INTO payments(id, member_id, product_id, pay_method, amount, card_num, card_name, paid_at, imp_uid) VALUES ('order_no_2021112201', 1, 1, 'card', 10, '1111-2222-3333-4441', 'cardA', 1637520153, 'imp_uid1');
INSERT INTO payments(id, member_id, product_id, pay_method, amount, card_num, card_name, paid_at, imp_uid) VALUES ('order_no_2021112202', 2, 1, 'card', 10, '1111-2222-3333-4442', 'cardB', 1637520156, 'imp_uid1');
INSERT INTO payments(id, member_id, product_id, pay_method, amount, card_num, card_name, paid_at, imp_uid) VALUES ('order_no_2021112203', 3, 2, 'card', 30, '1111-2222-3333-4443', 'cardC', 1637520169, 'imp_uid1');
INSERT INTO payments(id, member_id, product_id, pay_method, amount, card_num, card_name, paid_at, imp_uid) VALUES ('order_no_2021112204', 4, 1, 'card', 10, '1111-2222-3333-4444', 'cardD', 1637523753, 'imp_uid1');
INSERT INTO payments(id, member_id, product_id, pay_method, amount, card_num, card_name, paid_at, imp_uid) VALUES ('order_no_2021112205', 11, 1, 'card', 10, '1111-2222-3333-4445', 'cardE', 1637541753, 'imp_uid1');
INSERT INTO payments(id, member_id, product_id, pay_method, amount, card_num, card_name, paid_at, imp_uid) VALUES ('order_no_2021112206', 12, 2, 'card', 30, '1111-2222-3333-4446', 'cardF', 1637545353, 'imp_uid1');

-- 주문 예제 데이터
INSERT INTO orders(id, member_id, product_id, start_date, end_date) VALUES (1, 1, 1, '2021-11-12 12:48:01', '2021-12-12 12:48:01');
INSERT INTO orders(member_id, product_id, start_date, end_date) VALUES (2, 1, '2021-11-12 12:48:01', '2021-12-12 12:48:01');
INSERT INTO orders(member_id, product_id, start_date, end_date) VALUES (3, 2, '2021-11-12 12:48:01', '2022-02-12 12:48:01');
INSERT INTO orders(member_id, product_id, start_date, end_date) VALUES (4, 1, '2021-11-12 12:48:01', '2021-12-12 12:48:01');
INSERT INTO orders(member_id, product_id, start_date, end_date) VALUES (11, 1, '2021-11-12 12:48:01', '2021-12-12 12:48:01');
INSERT INTO orders(member_id, product_id, start_date, end_date) VALUES (12, 2, '2021-11-12 12:48:01', '2022-02-12 12:48:01');
