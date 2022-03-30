CREATE table application
(
    id              INT NOT NULL AUTO_INCREMENT,
    applicationType TINYINT,
    idApplicant     INT NOT NULL,
    status          TINYINT,
    PRIMARY KEY (id)
);