-- migration to platform-45

-- EXP-2954
CREATE TABLE `Core`.`StartPageMessage` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`active` BOOL NOT NULL DEFAULT '1',
	`message` TINYTEXT NOT NULL,
	`messageDate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Core`.`StartPageMessageLog` (
	`startPageMessage` INT NOT NULL,
	`transaction` BIGINT NOT NULL,
	`active` BOOL DEFAULT NULL,
	`message` TINYTEXT DEFAULT NULL,
	PRIMARY KEY (`startPageMessage`, `transaction`),
	CONSTRAINT `startPageMessage` FOREIGN KEY (`startPageMessage`) REFERENCES `Core`.`StartPageMessage` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `transaction` FOREIGN KEY (`transaction`) REFERENCES `Core`.`Transaction` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);
