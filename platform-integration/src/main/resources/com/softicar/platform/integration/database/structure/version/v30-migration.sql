-- migration from platform-25 to platform-30

-- -------- PLAT-1260-1 --------

ALTER TABLE `Core`.`User`
	ADD COLUMN `preferencesJson` TEXT NOT NULL DEFAULT '' AFTER `allowedIpRule`;

ALTER TABLE `Core`.`UserLog`
	ADD COLUMN `preferencesJson` TEXT DEFAULT NULL AFTER `allowedIpRule`;

ALTER TABLE `Core`.`User`
	DROP COLUMN `automaticallyCollapseFolders`,
	DROP COLUMN `recursivelyCollapseFolders`;

ALTER TABLE `Core`.`UserLog`
	DROP COLUMN `automaticallyCollapseFolders`,
	DROP COLUMN `recursivelyCollapseFolders`;
