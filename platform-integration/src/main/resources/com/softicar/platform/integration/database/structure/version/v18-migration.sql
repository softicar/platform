-- Migration from platform-17 to platform-18

ALTER TABLE `Core`.`User`
	ADD COLUMN `automaticallyCollapseFolders` BOOL NOT NULL DEFAULT '1' AFTER `localization`,
	ADD COLUMN `recursivelyCollapseFolders` BOOL NOT NULL DEFAULT '0' AFTER `automaticallyCollapseFolders`
;

ALTER TABLE `Core`.`UserLog`
	ADD COLUMN `automaticallyCollapseFolders` BOOL DEFAULT NULL AFTER `localization`,
	ADD COLUMN `recursivelyCollapseFolders` BOOL DEFAULT NULL AFTER `automaticallyCollapseFolders`
;
