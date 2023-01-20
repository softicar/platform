-- migration from platform-31 to platform-32

-- PLAT-1282

ALTER TABLE `Core`.`LogMessage` CHANGE COLUMN `logText` `logText` MEDIUMTEXT NOT NULL;

-- PLAT-1242

CREATE TABLE `Workflow`.`WorkflowMessageSeverity` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`)
) COMMENT='@enum@';

INSERT INTO `Workflow`.`WorkflowMessageSeverity` (id,name) VALUES (1,'VERBOSE');
INSERT INTO `Workflow`.`WorkflowMessageSeverity` (id,name) VALUES (2,'INFO');
INSERT INTO `Workflow`.`WorkflowMessageSeverity` (id,name) VALUES (3,'WARNING');
INSERT INTO `Workflow`.`WorkflowMessageSeverity` (id,name) VALUES (4,'ERROR');

ALTER TABLE `Workflow`.`WorkflowItemMessage`
	ADD COLUMN `severity` INT NOT NULL DEFAULT '2',
	ADD KEY `severity` (`severity`),
	ADD CONSTRAINT `WorkflowItemMessage_ibfk_3` FOREIGN KEY (`severity`) REFERENCES `Workflow`.`WorkflowMessageSeverity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
