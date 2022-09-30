DROP TABLE IF EXISTS account_transaction;
CREATE TABLE account_transaction (
transactionId VARCHAR(50) PRIMARY KEY,
accountId VARCHAR(50) NOT NULL,
operationId VARCHAR(50) NOT NULL,
accountingDate TIMESTAMP,
valueDate TIMESTAMP,
type VARCHAR(50),
amount DECIMAL(20,2),
currency VARCHAR(10),
description VARCHAR(50)
);