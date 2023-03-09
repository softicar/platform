-- migration from platform-33 to platform-38

-- -------------------------------- PLAT-1331 --------------------------------

-- StoredFileServer/StoredFileRepository

RENAME TABLE `Core`.`StoredFileServer` TO `Core`.`StoredFileRepository`;
ALTER TABLE `Core`.`StoredFileRepository`
	CHANGE COLUMN `domain` `domain` VARCHAR(255) NOT NULL DEFAULT '',
	CHANGE COLUMN `username` `username` VARCHAR(255) NOT NULL DEFAULT '',
	CHANGE COLUMN `password` `password` VARCHAR(255) NOT NULL DEFAULT ''
;

-- StoredFileServerLog/StoredFileRepositoryLog

RENAME TABLE `Core`.`StoredFileServerLog` TO `Core`.`StoredFileRepositoryLog`;
ALTER TABLE `Core`.`StoredFileRepositoryLog`
	RENAME COLUMN `storedFileServer` TO `storedFileRepository`
;

-- CoreModuleInstance
ALTER TABLE `Core`.`CoreModuleInstance`
	DROP CONSTRAINT `CoreModuleInstance_ibfk_3`,
	DROP KEY `primaryFileServer`,
	CHANGE COLUMN `primaryFileServer` `primaryFileRepository` INT DEFAULT NULL;
ALTER TABLE `Core`.`CoreModuleInstance`
	ADD KEY `primaryFileRepository` (`primaryFileRepository`),
	ADD CONSTRAINT `CoreModuleInstance_ibfk_3` FOREIGN KEY (`primaryFileRepository`) REFERENCES `Core`.`StoredFileRepository` (`id`)
;

--  CoreModuleInstanceLog
ALTER TABLE `Core`.`CoreModuleInstanceLog`
	DROP CONSTRAINT `CoreModuleInstanceLog_ibfk_4`,
	DROP KEY `primaryFileServer`,
	RENAME COLUMN `primaryFileServer` TO `primaryFileRepository`;
ALTER TABLE `Core`.`CoreModuleInstanceLog`
	ADD KEY `primaryFileRepository` (`primaryFileRepository`),
	ADD CONSTRAINT `CoreModuleInstanceLog_ibfk_4` FOREIGN KEY (`primaryFileRepository`) REFERENCES `Core`.`StoredFileRepository` (`id`)
;
