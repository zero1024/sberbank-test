CREATE TABLE ACCOUNT (
  ID     BIGINT                AUTO_INCREMENT,
  NUMBER VARCHAR2(50) NOT NULL UNIQUE,
  MONEY  DECIMAL      NOT NULL DEFAULT 0,
  PRIMARY KEY (ID),
);

ALTER TABLE ACCOUNT
  ADD CONSTRAINT MIN_MONEY CHECK MONEY >= 0;