-- migration to platform-50

-- PLAT-146
CREATE TABLE `Core`.`UserPasswordResetRequest` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`user` INT NOT NULL,
	`uuid` VARCHAR(255) NOT NULL,
	`createdAt` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`active` BOOL NOT NULL DEFAULT '1',
	PRIMARY KEY (`id`),
	KEY `user` (`user`),
	UNIQUE KEY `uuid` (`uuid`),
	CONSTRAINT `UserPasswordReset_ibfk_1` FOREIGN KEY (`user`) REFERENCES `Core`.`User` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);
