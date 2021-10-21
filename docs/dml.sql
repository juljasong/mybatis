-- 회원 예제 데이터
INSERT INTO members(id, email, pwd, name, create_date) VALUES (1, 'yh0921k@gmail.com', PASSWORD('1111'), '김용희', '2020-06-04');
INSERT INTO members(email, pwd, name, create_date) VALUES ('moonsolid@naver.com', PASSWORD('2222'), '문국태', '2020-06-05');
INSERT INTO members(email, pwd, name, create_date) VALUES ('oreotaste@daum.net', PASSWORD('3333'), '손영쿡', '2020-06-06');
INSERT INTO members(email, pwd, name) VALUES ('zpion@naver.com', PASSWORD('1111111111'), '송율리아');

-- url 예제 데이터
INSERT INTO urls(id, member_id, name, url, expirationDate) VALUES (1, 1, 'hello', 'https://www.test.com', '2022-06-04');
INSERT INTO urls(member_id, name, url, expirationDate) VALUES (4, 'AAA', 'https://www.aaa.co.kr', '2022-06-05');
INSERT INTO urls(member_id, name, url) VALUES (4, 'BBB', 'https://www.bbb.com');
INSERT INTO urls(member_id, name, url) VALUES (5, 'ccc', 'https://www.ccc.com');
