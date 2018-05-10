CREATE TABLE ROOM(
ID NUMBER NOT NULL PRIMARY KEY,
ID_HOTEL NUMBER,
CONSTRAINT ROOM_FK FOREIGN KEY(ID_HOTEL)REFERENCES HOTEL(ID),
NUMBER_OF_GUESTS NUMBER NOT NULL,
PRICE NUMBER(*, 2) NOT NULL,
BREAKFAST_INCLUDED NUMBER(1) CHECK (BREAKFAST_INCLUDED = 1 OR BREAKFAST_INCLUDED = 0),
PETS_ALLOWED NUMBER(1) CHECK (PETS_ALLOWED = 1 OR PETS_ALLOWED = 0),
DATE_AVAILABLE_FROM TIMESTAMP NOT NULL
);