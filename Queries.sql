-- Selecting the database
USE bankapp;

-- Creating customers table
CREATE TABLE `customer`(
	`customerId` INT AUTO_INCREMENT,
	`customerName` VARCHAR(20),
    PRIMARY KEY(`customerId`)
);

-- Creating accounts table
CREATE TABLE `account`(
	`accountNumber` INT AUTO_INCREMENT,
    `customerId` INT,
    `balance` INT DEFAULT 0,
    PRIMARY KEY(`accountNumber`),
    FOREIGN KEY(`customerId`) REFERENCES `customer`(`customerId`)
);

-- Creating transactions table
CREATE TABLE `transaction`(
	`transactionId` INT AUTO_INCREMENT,
    `amount` INT,
    `finalBalance` INT,
    `transactionDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `fromAccount` INT,
    `toAccount` INT,
	PRIMARY KEY(`transactionId`),
    FOREIGN KEY (`fromAccount`) REFERENCES account(`accountNumber`),
    FOREIGN KEY (`toAccount`) REFERENCES account(`accountNumber`)
);

-- Populating customer table
INSERT INTO `customer`(`customerName`)
	VALUES
    ('Vivek'),('Himanshu'),('Garvit'),('Basudev'),('Ashish'),('Mohan'),('Jigyasu'),('Harshit'),('Swarnim'),('Sagar')
;

-- Populating accounts table
INSERT INTO `account`(`customerId`)
	VALUES
    (1),(2),(3),(4),(5),(6),(7),(8),(9),(10)
;

-- Dummy customer value
INSERT INTO `customer`(`customerId`,`customerName`)
VALUES(-1,'Test');

 -- Dummy account value
INSERT INTO `account`(`customerId`,`accountNumber`,`balance`)
VALUES(-1,-1,0);

-- select * from customer;
-- select * from account;
-- select * from transaction; 

-- drop table transaction;
-- drop table account;
-- drop table customer;