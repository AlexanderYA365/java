ALTER TABLE `account`
    ADD COLUMN `username` VARCHAR(45) NOT NULL,
    ADD COLUMN `password` VARCHAR(45) NOT NULL AFTER `username`;
