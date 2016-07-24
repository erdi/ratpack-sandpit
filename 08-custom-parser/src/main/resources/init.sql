CREATE SCHEMA IF NOT EXISTS `lab08`;

USE `lab08`;

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (`isbn` CHAR(13) PRIMARY KEY, `quantity` INT, `price` NUMERIC(15, 2), `title` VARCHAR(256), `author` VARCHAR(128), `publisher` VARCHAR(128));
