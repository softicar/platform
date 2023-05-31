-- migration to platform-46

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
