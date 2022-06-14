CREATE TABLE users (
    no NUMBER,
    id VARCHAR2(20) UNIQUE NOT NULL,
    password VARCHAR(20) NOT NULL,
    name VARCHAR2(20) NOT NULL,
    gender VARCHAR2(10),
    PRIMARY KEY (no)
);

DROP TABLE users;


CREATE SEQUENCE seq_users_no
INCREMENT BY 1
START WITH 1
NOCACHE;

DROP SEQUENCE seq_users_no;


INSERT INTO users VALUES(seq_users_no.NEXTVAL,);


UPDATE users
SET password = 'test'
WHERE id = '테스트';





CREATE TABLE guestbook(
    no NUMBER,
    name VARCHAR2(80),
    password VARCHAR2(20),
    content VARCHAR2(2000),
    reg_date DATE,
    PRIMARY KEY (no)
);

DROP TABLE guestbook;

CREATE SEQUENCE seq_guestbook_no
INCREMENT BY 1
START WITH 1
NOCACHE;





CREATE TABLE board (
    no NUMBER,
    title VARCHAR2(500) NOT NULL,
    content VARCHAR2(4000),
    hit NUMBER DEFAULT 0,
    reg_date DATE NOT NULL,
    user_no NUMBER NOT NULL,
    PRIMARY KEY (no),
    CONSTRAINT board_fk FOREIGN KEY (user_no)
    REFERENCES users(no)
);

DROP TABLE board;

CREATE SEQUENCE seq_board_no
INCREMENT BY 1
START WITH 1
NOCACHE;

DROP SEQUENCE seq_guestbook_no;

UPDATE board
SET hit = (SELECT hit FROM board WHERE no = 1) + 1
WHERE no = 1;






COMMIT;

ROLLBACK;


SELECT * 
FROM users;

SELECT *
FROM guestbook;

SELECT *
FROM board;

SELECT b.no, b.title, u.name, b.hit, to_char(b.reg_date, 'yyyy-mm-dd')
FROM board b, users u
WHERE b.user_no = u.no;