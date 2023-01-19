-- migration from platform-31 to platform-32

-- PLAT-1282

ALTER TABLE `Core`.`LogMessage` CHANGE COLUMN `logText` `logText` MEDIUMTEXT NOT NULL;

