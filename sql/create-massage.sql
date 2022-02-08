CREATE TABLE massage
(
    `id`              INT          NOT NULL,
    `idSender`        INT          NOT NULL,
    `idReceiving`     INT          NOT NULL,
    `massage`         VARCHAR(150) NULL,
    `picture`         VARCHAR(150) NULL,
    `publicationDate` DATE         NULL,
    `edited`          TINYINT      NULL,
    PRIMARY KEY (`id`)
);