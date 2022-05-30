-- Migration from platform-18 to platform-19

CREATE TABLE `Core`.`MaintenanceState` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`)
) COMMENT='@enum@';

INSERT INTO `Core`.`MaintenanceState` VALUES
	(1, 'Pending'),
	(2, 'In Progress'),
	(3, 'Finished'),
	(4, 'Canceled')
;

CREATE TABLE `Core`.`MaintenanceWindow` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`expectedStart` DATETIME NOT NULL,
	`expectedEnd` DATETIME NOT NULL,
	`state` INT NOT NULL,
	`comment` TINYTEXT DEFAULT '',
	PRIMARY KEY (`id`),
	CONSTRAINT `state` FOREIGN KEY (`state`) REFERENCES `Core`.`MaintenanceState` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE `Core`.`MaintenanceWindowLog` (
	`maintenanceWindow` INT NOT NULL,
	`transaction` BIGINT NOT NULL,
	`expectedStart` DATETIME DEFAULT NULL,
	`expectedEnd` DATETIME DEFAULT NULL,
	`state` INT DEFAULT NULL,
	`comment` TINYTEXT DEFAULT NULL,
	PRIMARY KEY (`maintenanceWindow`, `transaction`),
	CONSTRAINT `Core_MaintenanceWindowLog_maintenanceWindow` FOREIGN KEY (`maintenanceWindow`) REFERENCES `Core`.`MaintenanceWindow` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `Core_MaintenanceWindowLog_transaction` FOREIGN KEY (`transaction`) REFERENCES `Core`.`Transaction` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `Core_MaintenanceWindowLog_state` FOREIGN KEY (`state`) REFERENCES `Core`.`MaintenanceState` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO `Core`.`UserLoginFailureType` VALUES
	(9, 'MAINTENANCE_IN_PROGRESS')
;
