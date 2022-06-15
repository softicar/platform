-- migration from platform-19 to platform-20

-- TODO change ID of core module instance from 1 to 0

-- migrate permission assignments for the core module
INSERT INTO `Core`.`ModuleInstancePermissionAssigment`
	(`transaction`, `user`, `moduleInstance`, `permission`, `active`)
SELECT a.`transaction`, a.`user`, 0, a.`permission`, a.`active`
FROM `Core`.`SystemModulePermissionAssignment` AS a
JOIN `Core`.`Uuid` AS uuid
WHERE a.`active`
AND uuid.`uuidString` = 'a8b076bd-582d-446d-9bce-85a8a180afd5'

DROP TABLE `Core`.`SystemModulePermissionAssignmentLog`;
DROP TABLE `Core`.`SystemModulePermissionAssignment`;
