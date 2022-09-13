-- migration from platform-23 to platform-24

ALTER TABLE `Core`.`ReplicationChecksum`
	CHANGE COLUMN `lower_boundary` `lower_boundary` TEXT DEFAULT NULL,
	CHANGE COLUMN `upper_boundary` `upper_boundary` TEXT DEFAULT NULL;
