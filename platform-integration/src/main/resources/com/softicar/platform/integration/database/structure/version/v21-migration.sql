-- migration from platform-20 to platform-21

RENAME TABLE `Core`.`ModuleInstance` TO `Core`.`ModuleInstanceBase`;

RENAME TABLE `Core`.`ModuleInstanceLog` TO `Core`.`ModuleInstanceBaseLog`;
ALTER TABLE `Core`.`ModuleInstanceBaseLog` RENAME COLUMN `moduleInstance` TO `moduleInstanceBase`;

ALTER TABLE `Core`.`BufferedEmail` ADD COLUMN `active` BOOL NOT NULL DEFAULT '1' AFTER `id`;

CREATE TABLE `Core`.`BufferedEmailLog` (
	`bufferedEmail` INT NOT NULL,
	`transaction` BIGINT NOT NULL,
	`active` BOOL DEFAULT NULL,
	PRIMARY KEY (`bufferedEmail`, `transaction`),
	FOREIGN KEY (`bufferedEmail`) REFERENCES `Core`.`BufferedEmail` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
	FOREIGN KEY (`transaction`) REFERENCES `Core`.`Transaction` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

ALTER TABLE `Core`.`CoreModuleInstance` RENAME COLUMN `moduleInstance` to `base`;

ALTER TABLE `Core`.`ModuleInstancePermissionAssignment` RENAME COLUMN `moduleInstance` TO `moduleInstanceBase`;
ALTER TABLE `Core`.`ModuleInstancePermissionAssignment` RENAME KEY `moduleInstance` TO `moduleInstanceBase`;
ALTER TABLE `Core`.`ModuleInstancePermissionAssignment` RENAME KEY `userModuleInstancePermission` TO `userModuleInstanceBasePermission`;

ALTER TABLE `Core`.`ProgramExecution` ADD COLUMN `maximumRuntimeExceeded` BOOL NOT NULL DEFAULT '0' AFTER `output`;

ALTER TABLE `Core`.`ProgramExecutionLog` ADD COLUMN `maximumRuntimeExceeded` BOOL DEFAULT NULL AFTER `output`;

ALTER TABLE `Core`.`RolePermission`  RENAME COLUMN `moduleInstance` TO `moduleInstanceBase`;
ALTER TABLE `Core`.`RolePermission`  RENAME KEY `moduleInstance` TO `moduleInstanceBase`;
ALTER TABLE `Core`.`RolePermission`  RENAME KEY `rolePermissionUuidModuleInstance` TO `rolePermissionUuidModuleInstanceBase`;

ALTER TABLE `Core`.`RolePermissionLog`  RENAME COLUMN `moduleInstance` TO `moduleInstanceBase`;
ALTER TABLE `Core`.`RolePermissionLog`  RENAME KEY `moduleInstance` TO `moduleInstanceBase`;

ALTER TABLE `Core`.`ScheduledProgramExecution` ADD COLUMN `maximumRuntime` INT DEFAULT NULL AFTER `programUuid`;
ALTER TABLE `Core`.`ScheduledProgramExecution` ADD COLUMN `automaticAbort` BOOL NOT NULL DEFAULT '0' AFTER `maximumRuntime`;

ALTER TABLE `Core`.`ScheduledProgramExecutionLog` ADD COLUMN `maximumRuntime` INT DEFAULT NULL AFTER `cronExpression`;
ALTER TABLE `Core`.`ScheduledProgramExecutionLog` ADD COLUMN `automaticAbort` BOOL DEFAULT NULL AFTER `maximumRuntime`;

ALTER TABLE `Workflow`.`WorkflowModuleInstance` RENAME COLUMN `moduleInstance` to `base`;

-- BEGIN DML
DELETE FROM `Core`.`UserSpecificTableConfiguration`;
-- END DML

ALTER TABLE `Core`.`UserSpecificTableConfiguration` CHANGE COLUMN `tableIdentifierHash` `tablePathHash` VARCHAR(255) NOT NULL DEFAULT '' AFTER `user`;
ALTER TABLE `Core`.`UserSpecificTableConfiguration` DROP INDEX `tableIdentifierHashUser`;
ALTER TABLE `Core`.`UserSpecificTableConfiguration` ADD UNIQUE INDEX `userTablePathHashColumnTitlesHash` (`user`, `tablePathHash`, `columnTitlesHash`);

ALTER TABLE `Core`.`Localization` ADD COLUMN `dateFormat` VARCHAR(255) NOT NULL AFTER `digitGroupSeparator`;
ALTER TABLE `Core`.`LocalizationLog` ADD COLUMN `dateFormat` VARCHAR(255) DEFAULT NULL AFTER `digitGroupSeparator`;

-- BEGIN DML
UPDATE `Core`.`Localization` SET `dateFormat` = "yyyy-MM-dd";
UPDATE `Core`.`Localization` SET `dateFormat` = "dd.MM.yyyy" WHERE `name` LIKE '%eutsch%';
-- END DML
