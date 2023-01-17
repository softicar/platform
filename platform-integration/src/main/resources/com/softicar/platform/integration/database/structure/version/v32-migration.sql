-- migration from platform-31 to platform-32

-- PLAT-1282

ALTER TABLE `Core`.`LogMessage` CHANGE COLUMN `logText` `logText` MEDIUMTEXT NOT NULL;

-- PLAT-1242

CREATE TABLE `Workflow`.`WorkflowMessageSeverity` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(0) NOT NULL,
	PRIMARY KEY (`id`)
) COMMENT='@enum@';

ALTER TABLE `Workflow`.`WorkflowItemMessage`
	ADD COLUMN `severity` INT NOT NULL DEFAULT '2',
	ADD KEY `severity` (`severity`),
	ADD CONSTRAINT `WorkflowItemMessage_ibfk_3` FOREIGN KEY (`severity`) REFERENCES `Workflow`.`WorkflowMessageSeverity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
