-- migration to platform-46

-- EXP-2954

CREATE TABLE `Core`.`StartPageMessage` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`active` BOOL NOT NULL DEFAULT '1',
	`message` MEDIUMTEXT NOT NULL,
	`messageDate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Core`.`StartPageMessageLog` (
	`startPageMessage` INT NOT NULL,
	`transaction` BIGINT NOT NULL,
	`active` BOOL DEFAULT NULL,
	`message` MEDIUMTEXT DEFAULT NULL,
	PRIMARY KEY (`startPageMessage`, `transaction`),
	CONSTRAINT `StartPageMessageLog_ibfk_1` FOREIGN KEY (`startPageMessage`) REFERENCES `Core`.`StartPageMessage` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `StartPageMessageLog_ibfk_2` FOREIGN KEY (`transaction`) REFERENCES `Core`.`Transaction` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

-- PLAT-1385

CREATE TABLE `Workflow`.`WorkflowSpecificUserConfiguration` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`user` INT NOT NULL,
	`workflow` INT NOT NULL,
	`subscribed` BOOL NOT NULL DEFAULT '1',
	PRIMARY KEY (`id`),
	UNIQUE KEY `userWorkflow` (`user`, `workflow`),
	KEY `workflow` (`workflow`),
	CONSTRAINT `WorkflowSpecificUserConfiguration_ibfk_1` FOREIGN KEY (`user`) REFERENCES `Core`.`User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `WorkflowSpecificUserConfiguration_ibfk_2` FOREIGN KEY (`workflow`) REFERENCES `Workflow`.`Workflow` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `Workflow`.`WorkflowSpecificUserConfigurationLog` (
	`configuration` INT NOT NULL,
	`transaction` BIGINT NOT NULL,
	`subscribed` BOOL DEFAULT NULL,
	PRIMARY KEY (`configuration`, `transaction`),
	KEY `transaction` (`transaction`),
	CONSTRAINT `WorkflowSpecificUserConfigurationLog_ibfk_1` FOREIGN KEY (`configuration`) REFERENCES `Workflow`.`WorkflowSpecificUserConfiguration` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `WorkflowSpecificUserConfigurationLog_ibfk_2` FOREIGN KEY (`transaction`) REFERENCES `Core`.`Transaction` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
