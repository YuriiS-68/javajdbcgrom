CREATE TABLE USER_DZ4(
ID NUMBER NOT NULL PRIMARY KEY,
USER_NAME NVARCHAR2(20) NOT NULL,
PASSWORD NVARCHAR2(20) NOT NULL,
COUNTRY NVARCHAR2(20) NOT NULL,
USER_TYPE NVARCHAR2(20) NOT NULL CHECK (USER_TYPE = 'ADMIN' OR USER_TYPE = 'USER')
);

CREATE SEQUENCE USER_SEQ MINVALUE 1 MAXVALUE 1000 START WITH 1 INCREMENT BY 1;