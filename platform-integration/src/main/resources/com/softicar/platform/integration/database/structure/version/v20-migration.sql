-- migration from platform-19 to platform-20


-- allow insertion of 0 in auto_increment columns --

SELECT @@SESSION.SQL_MODE INTO @sqlMode;
SET SESSION SQL_MODE = CONCAT(@sqlMode, ',NO_AUTO_VALUE_ON_ZERO');


-- create a transaction --
SELECT `systemUser` INTO @systemUser FROM `Core`.`CoreModuleInstance` LIMIT 1;
INSERT INTO `Core`.`Transaction` SET `at` = NOW(), `by` = @systemUser;
SELECT LAST_INSERT_ID() INTO @transactionId;


-- change referential action of FK in `Core`.`CoreModuleInstanceLog` --

SELECT `CONSTRAINT_NAME` INTO @constraintName FROM `information_schema`.`KEY_COLUMN_USAGE` WHERE `TABLE_SCHEMA` = 'Core' AND `TABLE_NAME` = 'CoreModuleInstanceLog' AND `COLUMN_NAME` = 'coreModuleInstance' AND `CONSTRAINT_NAME` != 'PRIMARY';
SET @runString = CONCAT('ALTER TABLE `Core`.`CoreModuleInstanceLog` DROP CONSTRAINT ', @constraintName);
PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

ALTER TABLE `Core`.`CoreModuleInstanceLog` ADD FOREIGN KEY (`coreModuleInstance`) REFERENCES `Core`.`CoreModuleInstance` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;


-- change ID of core module instance from 1 to 0 --

UPDATE `Core`.`CoreModuleInstance` SET `id` = 0;


-- insert new ModuleInstance record for the singleton core module instance --

SELECT `id` INTO @uuidId FROM `Core`.`Uuid` WHERE `uuidString` = 'a8b076bd-582d-446d-9bce-85a8a180afd5';
INSERT INTO `Core`.`ModuleInstance` SET `id` = 0, `transaction` = @transactionId, `moduleUuid` = @uuidId;


-- change referential action of FK in `Core`.`AjaxException` --

SELECT `CONSTRAINT_NAME` INTO @constraintName FROM `information_schema`.`KEY_COLUMN_USAGE` WHERE `TABLE_SCHEMA` = 'Core' AND `TABLE_NAME` = 'AjaxException' AND `COLUMN_NAME` = 'session';
SET @runString = CONCAT('ALTER TABLE `Core`.`AjaxException` DROP CONSTRAINT ', @constraintName);
PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

ALTER TABLE `Core`.`AjaxException` ADD FOREIGN KEY (`session`) REFERENCES `Core`.`AjaxSession` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;


-- migrate CoreModuleInstance --

ALTER TABLE `Core`.`CoreModuleInstance` ADD COLUMN `systemName` VARCHAR(255) NOT NULL DEFAULT 'Forspace' AFTER `portalApplication`;
ALTER TABLE `Core`.`CoreModuleInstance` RENAME COLUMN `id` TO `moduleInstance`;
ALTER TABLE `Core`.`CoreModuleInstance` CHANGE COLUMN `moduleInstance` `moduleInstance` INT NOT NULL COMMENT '@base@';
ALTER TABLE `Core`.`CoreModuleInstance` ADD FOREIGN KEY (`moduleInstance`) REFERENCES `Core`.`ModuleInstance` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

SELECT `systemName` INTO @systemName FROM `Core`.`LiveSystemConfiguration` AS lsc LIMIT 1;
UPDATE `Core`.`CoreModuleInstance` SET `systemName` = IFNULL(@systemName, 'Forspace');


-- migrate CoreModuleInstanceLog

ALTER TABLE `Core`.`CoreModuleInstanceLog` ADD COLUMN `systemName` VARCHAR(255) DEFAULT NULL AFTER `portalApplication`;


-- drop live system configuration tables --

DROP TABLE `Core`.`LiveSystemConfigurationLog`;
DROP TABLE `Core`.`LiveSystemConfiguration`;


-- rename old role tables --

ALTER TABLE `Core`.`ModuleInstanceRoleAssignmentLog` RENAME COLUMN `role` TO `permission`;
ALTER TABLE `Core`.`ModuleInstanceRoleAssignmentLog` RENAME KEY `role` TO `permission`;
RENAME TABLE `Core`.`ModuleInstanceRoleAssignmentLog` TO `Core`.`ModuleInstancePermissionAssignmentLog`;

ALTER TABLE `Core`.`ModuleInstanceRoleAssignment` RENAME COLUMN `role` TO `permission`;
ALTER TABLE `Core`.`ModuleInstanceRoleAssignment` RENAME KEY `role` TO `permission`;
ALTER TABLE `Core`.`ModuleInstanceRoleAssignment` RENAME KEY `userModuleInstanceRole` TO `userModuleInstancePermission`;
RENAME TABLE `Core`.`ModuleInstanceRoleAssignment` TO `Core`.`ModuleInstancePermissionAssignment`;


-- migrate permission assignments for the core module --

INSERT INTO `Core`.`ModuleInstancePermissionAssignment`
	(`transaction`, `user`, `moduleInstance`, `permission`, `active`)
SELECT a.`transaction`, a.`user`, 0, a.`role`, a.`active`
FROM `Core`.`SystemModuleRoleAssignment` AS a
JOIN `Core`.`Uuid` AS uuid ON uuid.`id` = a.`module`
WHERE a.`active`
AND uuid.`uuidString` = 'a8b076bd-582d-446d-9bce-85a8a180afd5';


-- drop old system module role tables --

DROP TABLE `Core`.`SystemModuleRoleAssignmentLog`;
DROP TABLE `Core`.`SystemModuleRoleAssignment`;


-- create new role tables --

CREATE TABLE `Core`.`Role` (
`id` INT NOT NULL AUTO_INCREMENT,
`active` BOOL NOT NULL DEFAULT '1',
`name` VARCHAR(255) NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `name` (`name`)
);

CREATE TABLE `Core`.`RoleLog` (
`role` INT NOT NULL,
`transaction` BIGINT NOT NULL,
`active` BOOL DEFAULT NULL,
`name` VARCHAR(255) DEFAULT NULL,
PRIMARY KEY (`role`, `transaction`),
FOREIGN KEY (`role`) REFERENCES `Core`.`Role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`transaction`) REFERENCES `Core`.`Transaction` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE `Core`.`RolePermission` (
`id` INT NOT NULL AUTO_INCREMENT,
`active` BOOL NOT NULL DEFAULT '1',
`role` INT NOT NULL,
`moduleInstance` INT NOT NULL,
`permissionUuid` INT NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `rolePermissionUuidModuleInstance` (`role`, `permissionUuid`, `moduleInstance`),
FOREIGN KEY (`role`) REFERENCES `Core`.`Role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`moduleInstance`) REFERENCES `Core`.`ModuleInstance` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`permissionUuid`) REFERENCES `Core`.`Uuid` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE `Core`.`RolePermissionLog` (
`roleUser` INT NOT NULL,
`transaction` BIGINT NOT NULL,
`active` BOOL DEFAULT NULL,
`role` INT DEFAULT NULL,
`moduleInstance` INT DEFAULT NULL,
`permissionUuid` INT DEFAULT NULL,
PRIMARY KEY (`roleUser`, `transaction`),
FOREIGN KEY (`roleUser`) REFERENCES `Core`.`RolePermission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`transaction`) REFERENCES `Core`.`Transaction` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`role`) REFERENCES `Core`.`Role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`moduleInstance`) REFERENCES `Core`.`ModuleInstance` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`permissionUuid`) REFERENCES `Core`.`Uuid` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE `Core`.`RoleUser` (
`id` INT NOT NULL AUTO_INCREMENT,
`active` BOOL NOT NULL DEFAULT '1',
`role` INT NOT NULL,
`user` INT NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `roleUser` (`role`, `user`),
FOREIGN KEY (`role`) REFERENCES `Core`.`Role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`user`) REFERENCES `Core`.`User` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE `Core`.`RoleUserLog` (
`roleUser` INT NOT NULL,
`transaction` BIGINT NOT NULL,
`active` BOOL DEFAULT NULL,
`role` INT DEFAULT NULL,
`user` INT DEFAULT NULL,
PRIMARY KEY (`roleUser`, `transaction`),
FOREIGN KEY (`roleUser`) REFERENCES `Core`.`RoleUser` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`transaction`) REFERENCES `Core`.`Transaction` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`role`) REFERENCES `Core`.`Role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`user`) REFERENCES `Core`.`User` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);


-- create system event tables --

CREATE TABLE `Core`.`SystemEventSeverity` (
`id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(255) NOT NULL,
`needsConfirmation` BOOL NOT NULL,
PRIMARY KEY (`id`)
) COMMENT='@enum@';

CREATE TABLE `Core`.`SystemEvent` (
`id` INT NOT NULL AUTO_INCREMENT,
`needsConfirmation` BOOL NOT NULL,
`severity` INT NOT NULL,
`causedAt` DATETIME NOT NULL,
`causedBy` INT NOT NULL,
`message` TEXT NOT NULL,
`properties` TEXT NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`severity`) REFERENCES `Core`.`SystemEventSeverity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`causedBy`) REFERENCES `Core`.`User` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE `Core`.`SystemEventLog` (
`systemEvent` INT NOT NULL,
`transaction` BIGINT NOT NULL,
`needsConfirmation` BOOL DEFAULT NULL,
PRIMARY KEY (`systemEvent`, `transaction`),
FOREIGN KEY (`systemEvent`) REFERENCES `Core`.`SystemEvent` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`transaction`) REFERENCES `Core`.`Transaction` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);


-- rename workflow tables --

ALTER TABLE `Workflow`.`WorkflowNodeActionRole` RENAME COLUMN `roleUuid` TO `permissionUuid`;
ALTER TABLE `Workflow`.`WorkflowNodeActionRole` RENAME KEY `roleUuid` TO `permissionUuid`;
RENAME TABLE `Workflow`.`WorkflowNodeActionRole` TO `Workflow`.`WorkflowNodeActionPermission`;

ALTER TABLE `Workflow`.`WorkflowNodeActionRoleLog` RENAME COLUMN `workflowNodeActionRole` TO `workflowNodeActionPermission`;
ALTER TABLE `Workflow`.`WorkflowNodeActionRoleLog` RENAME COLUMN `roleUuid` TO `permissionUuid`;
ALTER TABLE `Workflow`.`WorkflowNodeActionRoleLog` RENAME KEY `roleUuid` TO `permissionUuid`;
RENAME TABLE `Workflow`.`WorkflowNodeActionRoleLog` TO `Workflow`.`WorkflowNodeActionPermissionLog`;

ALTER TABLE `Workflow`.`WorkflowTransitionRole` RENAME COLUMN `role` TO `permission`;
ALTER TABLE `Workflow`.`WorkflowTransitionRole` RENAME KEY `role` TO `permission`;
RENAME TABLE `Workflow`.`WorkflowTransitionRole` TO `Workflow`.`WorkflowTransitionPermission`;

ALTER TABLE `Workflow`.`WorkflowTransitionRoleLog` RENAME COLUMN `transitionRole` TO `transitionPermission`;
ALTER TABLE `Workflow`.`WorkflowTransitionRoleLog` RENAME COLUMN `role` TO `permission`;
ALTER TABLE `Workflow`.`WorkflowTransitionRoleLog` RENAME KEY `role` TO `permission`;
RENAME TABLE `Workflow`.`WorkflowTransitionRoleLog` TO `Workflow`.`WorkflowTransitionPermissionLog`;
