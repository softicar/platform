-- migration from platform-31 to platform-32

-- PLAT-1282

ALTER TABLE `Core`.`LogMessage` CHANGE COLUMN `logText` `logText` MEDIUMTEXT NOT NULL;

-- PLAT-1182-2

ALTER TABLE `Core`.`Localization`
	ADD COLUMN `localizedDateFormat` VARCHAR(255) NOT NULL,
	ADD COLUMN `localizedTimeFormat` VARCHAR(255) NOT NULL
;

ALTER TABLE `Core`.`LocalizationLog`
	ADD COLUMN `localizedDateFormat` VARCHAR(255) DEFAULT NULL,
	ADD COLUMN `localizedTimeFormat` VARCHAR(255) DEFAULT NULL
;

UPDATE `Core`.`Localization` SET `localizedDateFormat` = 'TT.MM.JJJJ' WHERE `name` IN('German', 'Deutsch');
UPDATE `Core`.`Localization` SET `localizedTimeFormat` = 'SS:MM:ss' WHERE `name` IN('German', 'Deutsch');
UPDATE `Core`.`Localization` SET `localizedDateFormat` = 'YYYY-MM-DD' WHERE `localizedDateFormat` = '';
UPDATE `Core`.`Localization` SET `localizedTimeFormat` = 'HH:MM:SS' WHERE `localizedTimeFormat` = '';
