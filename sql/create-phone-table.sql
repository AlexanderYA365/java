CREATE TABLE Phone
(
    `id`          INT AUTO_INCREMENT NOT NULL,
    `phoneNumber` VARCHAR(25)        NOT NULL,
    `phoneType`   INT                NOT NULL,
    `idAccount`   INT                NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (idAccount) REFERENCES account (account_id)
);