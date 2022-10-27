-- migration from platform-25 to platform-26

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
