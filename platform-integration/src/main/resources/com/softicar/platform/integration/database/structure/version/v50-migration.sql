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

-- PLAT-1423
ALTER TABLE `Core`.`CoreModuleInstance` ADD `passwordResetFunctionality` TINYINT(1) NOT NULL DEFAULT '0' AFTER `testSystem`;
ALTER TABLE `Core`.`CoreModuleInstanceLog` ADD `passwordResetFunctionality` TINYINT(1) NULL DEFAULT NULL AFTER `testSystem`; 
