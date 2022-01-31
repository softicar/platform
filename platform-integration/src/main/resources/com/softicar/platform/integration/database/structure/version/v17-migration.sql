-- Migration from platform-16 to platform-17

--
-- LOCALIZATION
--

CREATE TABLE `Core`.`Localization` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`language` INT NOT NULL,
	`decimalSeparator` VARCHAR(255) NOT NULL,
	`digitGroupSeparator` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `name` (`name`),
	CONSTRAINT FOREIGN KEY (`language`) REFERENCES `Core`.`CoreLanguage` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO `Core`.`Localization` (`id`, `name`, `language`, `decimalSeparator`, `digitGroupSeparator`) VALUES (1, 'USA', 1, '.', ',');
INSERT INTO `Core`.`Localization` (`id`, `name`, `language`, `decimalSeparator`, `digitGroupSeparator`) VALUES (2, 'Deutschland', 2, ',', '.');

CREATE TABLE `Core`.`LocalizationLog` (
	`user` INT NOT NULL,
	`transaction` BIGINT NOT NULL,
	`name` VARCHAR(255) DEFAULT NULL,
	`language` INT DEFAULT NULL,
	`decimalSeparator` VARCHAR(255) DEFAULT NULL,
	`digitGroupSeparator` VARCHAR(255) DEFAULT NULL,
	PRIMARY KEY (`user`, `transaction`),
	CONSTRAINT FOREIGN KEY (`user`) REFERENCES `Core`.`Localization` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FOREIGN KEY (`transaction`) REFERENCES `Core`.`Transaction` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FOREIGN KEY (`language`) REFERENCES `Core`.`CoreLanguage` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);


--
-- CORE MODULE INSTANCE
--

ALTER TABLE `Core`.`CoreModuleInstance`
	ADD COLUMN `defaultLocalization` INT DEFAULT NULL AFTER `portalLogo`,
	ADD FOREIGN KEY (`defaultLocalization`) REFERENCES `Core`.`Localization` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

UPDATE `Core`.`CoreModuleInstance` SET `defaultLocalization` = 2;

ALTER TABLE `Core`.`CoreModuleInstance`
	MODIFY `defaultLocalization` INT NOT NULL
;

ALTER TABLE `Core`.`CoreModuleInstanceLog`
	ADD COLUMN `defaultLocalization` INT DEFAULT NULL AFTER `portalLogo`,
	ADD FOREIGN KEY (`defaultLocalization`) REFERENCES `Core`.`Localization` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
;


--
-- PROGRAM
--

SELECT `CONSTRAINT_NAME` INTO @programLogUuidFkName FROM `information_schema`.`KEY_COLUMN_USAGE` WHERE `TABLE_SCHEMA` = 'Core' AND `TABLE_NAME` = 'ProgramLog' AND `COLUMN_NAME` = 'programUuid';
SET @runString = CONCAT('ALTER TABLE `Core`.`ProgramLog` DROP CONSTRAINT ', @programLogUuidFkName);
PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

ALTER TABLE `Core`.`ProgramLog`
	DROP COLUMN `programUuid`
;


--
-- USER
--

ALTER TABLE `Core`.`User`
	ADD COLUMN `localization` INT DEFAULT NULL AFTER `preferredLanguage`,
	ADD FOREIGN KEY (`localization`) REFERENCES `Core`.`Localization` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

UPDATE `Core`.`User` SET `localization` = 1 WHERE `preferredLanguage` = 1;
UPDATE `Core`.`User` SET `localization` = 2 WHERE `preferredLanguage` = 2;

ALTER TABLE `Core`.`User`
	MODIFY `localization` INT NOT NULL
;

ALTER TABLE `Core`.`UserLog`
	ADD COLUMN `localization` INT DEFAULT NULL AFTER `preferredLanguage`,
	ADD FOREIGN KEY (`localization`) REFERENCES `Core`.`Localization` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

SELECT `CONSTRAINT_NAME` INTO @userPreferredLanguageFkName FROM `information_schema`.`KEY_COLUMN_USAGE` WHERE `TABLE_SCHEMA` = 'Core' AND `TABLE_NAME` = 'User' AND `COLUMN_NAME` = 'preferredLanguage';
SET @runString = CONCAT('ALTER TABLE `Core`.`User` DROP CONSTRAINT ', @userPreferredLanguageFkName);
PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

ALTER TABLE `Core`.`User` DROP COLUMN `preferredLanguage`;

SELECT `CONSTRAINT_NAME` INTO @userLogPreferredLanguageFkName FROM `information_schema`.`KEY_COLUMN_USAGE` WHERE `TABLE_SCHEMA` = 'Core' AND `TABLE_NAME` = 'UserLog' AND `COLUMN_NAME` = 'preferredLanguage';
SET @runString = CONCAT('ALTER TABLE `Core`.`UserLog` DROP CONSTRAINT ', @userLogPreferredLanguageFkName);
PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

ALTER TABLE `Core`.`UserLog` DROP COLUMN `preferredLanguage`;


--
-- WORKFLOW USER CONFIGURATION
--

CREATE TABLE `Workflow`.`WorkflowUserConfiguration` (
	`user` INT NOT NULL,
	`emailNotificationsForNewTasks` BOOL NOT NULL DEFAULT '1',
	`substitute` INT DEFAULT NULL,
	`substituteFrom` DATE DEFAULT NULL,
	`substituteTo` DATE DEFAULT NULL,
	PRIMARY KEY (`user`),
	CONSTRAINT FOREIGN KEY (`user`) REFERENCES `Core`.`User` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FOREIGN KEY (`substitute`) REFERENCES `Core`.`User` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE `Workflow`.`WorkflowUserConfigurationLog` (
	`workflowUserConfiguration` INT NOT NULL,
	`transaction` BIGINT NOT NULL,
	`emailNotificationsForNewTasks` BOOL DEFAULT NULL,
	`substitute` INT DEFAULT NULL,
	`substituteFrom` DATE DEFAULT NULL,
	`substituteTo` DATE DEFAULT NULL,
	PRIMARY KEY (`workflowUserConfiguration`, `transaction`),
	CONSTRAINT FOREIGN KEY (`workflowUserConfiguration`) REFERENCES `Workflow`.`WorkflowUserConfiguration` (`user`) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FOREIGN KEY (`transaction`) REFERENCES `Core`.`Transaction` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FOREIGN KEY (`substitute`) REFERENCES `Core`.`User` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO `Workflow`.`WorkflowUserConfiguration`
	(`user`, `emailNotificationsForNewTasks`, `substitute`, `substituteFrom`, `substituteTo`)
	SELECT
	`user`, 1, `substitute`, `validFrom`, `validTo`
	FROM `Workflow`.`WorkflowSubstitute`
	WHERE `active` = 1
;

DROP TABLE `Workflow`.`WorkflowSubstituteLog`;

DROP TABLE `Workflow`.`WorkflowSubstitute`;
