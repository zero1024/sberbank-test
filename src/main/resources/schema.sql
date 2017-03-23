CREATE TABLE ACCOUNT (
  ID     BIGINT                AUTO_INCREMENT,
  NUMBER VARCHAR2(12) NOT NULL,
  MONEY  DECIMAL      NOT NULL DEFAULT 0,
  PRIMARY KEY (ID),
  CHECK MONEY >= 0
);