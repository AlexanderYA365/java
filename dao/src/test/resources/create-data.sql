CREATE TABLE `account` (
  `idAccount` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `date` datetime DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `icq` int DEFAULT NULL,
  `addressHome` varchar(100) DEFAULT NULL,
  `addressJob` varchar(100) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `aboutMe` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`idAccount`)
);

CREATE TABLE `friends` (
                           `idFriends` int NOT NULL AUTO_INCREMENT,
                           `idAccount` int NOT NULL,
                           `idFriendsAccount` int NOT NULL,
                           PRIMARY KEY (`idFriends`)
);

CREATE TABLE `group` (
                         `idgroup` int NOT NULL AUTO_INCREMENT,
                         `groupname` varchar(45) NOT NULL,
                         `logo` varchar(45) DEFAULT NULL,
                         `idAdministrator` int NOT NULL,
                         `Account` int NOT NULL,
                         PRIMARY KEY (`idgroup`),
                         KEY `idAccount_idx` (`Account`),
                         CONSTRAINT `Account` FOREIGN KEY (`Account`) REFERENCES `account` (`idAccount`)
);

CREATE TABLE `phone` (
                         `idaccount` int DEFAULT NULL,
                         `phonenumber` varchar(45) DEFAULT NULL,
                         `sign` binary(1) DEFAULT NULL,
                         `idphone` int NOT NULL AUTO_INCREMENT,
                         PRIMARY KEY (`idphone`),
                         KEY `idAccount_idx` (`idaccount`),
                         CONSTRAINT `idAccount` FOREIGN KEY (`idaccount`) REFERENCES `account` (`idAccount`)
);