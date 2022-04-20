-- Migration from platform-17 to platform-18

ALTER TABLE `Core`.`User`
	ADD COLUMN `automaticallyCollapseFolders` BOOL NOT NULL DEFAULT '1' AFTER `localization`
;

ALTER TABLE `Core`.`UserLog`
	ADD COLUMN `automaticallyCollapseFolders` BOOL DEFAULT NULL AFTER `localization`
;
