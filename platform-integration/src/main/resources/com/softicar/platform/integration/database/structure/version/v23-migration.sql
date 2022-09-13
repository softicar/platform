-- migration from platform-22 to platform-23

-- ---------------- PLAT-1104 ----------------

DROP TABLE `Core`.`UserImpersonationState`;

-- ---------------- PLAT-1080 ----------------

-- BEGIN DML

-- fetch system user
SELECT `systemUser` INTO @systemUser FROM `Core`.`CoreModuleInstance` LIMIT 1;

-- create a transaction
INSERT INTO `Core`.`Transaction` SET `at` = NOW(), `by` = @systemUser;
SELECT LAST_INSERT_ID() INTO @transactionId;

-- fetch old permissions
SELECT `id` INTO @superUserUuidId FROM `Core`.`Uuid` WHERE `uuidString` = 'abeb1897-5079-4bf8-9f1b-6c7cd7e71890';
SELECT `id` INTO @accessManagementUuidId FROM `Core`.`Uuid` WHERE `uuidString` = 'c0b0e2ea-7475-4e39-addf-f977f5eb8986';
SELECT `id` INTO @systemAdministrationUuidId FROM `Core`.`Uuid` WHERE `uuidString` = 'e1bb654c-4a85-4c2f-b0a2-1116b2fca657';

-- create new permissions if necessary
INSERT IGNORE INTO `Core`.`Uuid` (`uuidBytes`, `uuidString`) VALUES
	(UNHEX(REPLACE('7f61eed0-c9c1-4ac3-a84d-254c8ddb3a6d', '-','')), '7f61eed0-c9c1-4ac3-a84d-254c8ddb3a6d'),
	(UNHEX(REPLACE('197bcbf7-0003-4552-8f4d-33d4f1d72a0b', '-','')), '197bcbf7-0003-4552-8f4d-33d4f1d72a0b');

-- fetch new permissions
SELECT `id` INTO @coreAdministrationUuidId FROM `Core`.`Uuid` WHERE `uuidString` = '7f61eed0-c9c1-4ac3-a84d-254c8ddb3a6d';
SELECT `id` INTO @coreOperationUuidId FROM `Core`.`Uuid` WHERE `uuidString` = '197bcbf7-0003-4552-8f4d-33d4f1d72a0b';

-- migrate direct permissions
INSERT INTO `Core`.`ModuleInstancePermissionAssignmentLog` (`assignment`, `transaction`, `active`, `permission`)
	SELECT `id`, @transactionId, `active`, @coreAdministrationUuidId
	FROM `Core`.`ModuleInstancePermissionAssignment`
	WHERE `permission` IN(@systemAdministrationUuidId, @accessManagementUuidId);
UPDATE IGNORE `Core`.`ModuleInstancePermissionAssignment`
	SET `permission` = @coreAdministrationUuidId, `transaction` = @transactionId
	WHERE `permission` IN(@systemAdministrationUuidId, @accessManagementUuidId);
DELETE FROM `Core`.`ModuleInstancePermissionAssignmentLog`
	WHERE `permission` IN(@systemAdministrationUuidId, @accessManagementUuidId);
DELETE FROM `Core`.`ModuleInstancePermissionAssignmentLog`
	WHERE `assignment` IN(
		SELECT `id` FROM `Core`.`ModuleInstancePermissionAssignment` WHERE `permission` IN(@systemAdministrationUuidId, @accessManagementUuidId)
	);
DELETE FROM `Core`.`ModuleInstancePermissionAssignment`
	WHERE `permission` IN(@systemAdministrationUuidId, @accessManagementUuidId);

INSERT INTO `Core`.`ModuleInstancePermissionAssignmentLog` (`assignment`, `transaction`, `active`, `permission`)
	SELECT `id`, @transactionId, `active`, @coreOperationUuidId
	FROM `Core`.`ModuleInstancePermissionAssignment`
	WHERE `permission` = @superUserUuidId;
UPDATE IGNORE `Core`.`ModuleInstancePermissionAssignment`
	SET `permission` = @coreOperationUuidId, `transaction` = @transactionId
	WHERE `permission` = @superUserUuidId;
DELETE FROM `Core`.`ModuleInstancePermissionAssignmentLog`
	WHERE `permission` = @superUserUuidId;
DELETE FROM `Core`.`ModuleInstancePermissionAssignmentLog`
	WHERE `assignment` IN(
		SELECT `id` FROM `Core`.`ModuleInstancePermissionAssignment` WHERE `permission` = @superUserUuidId
	);
DELETE FROM `Core`.`ModuleInstancePermissionAssignment`
	WHERE `permission` = @superUserUuidId;

-- migrate role permissions
INSERT INTO `Core`.`RolePermissionLog` (`roleUser`, `transaction`, `active`, `role`, `moduleInstanceBase`, `permissionUuid`)
	SELECT `id`, @transactionId, `active`, `role`, `moduleInstanceBase`, @coreAdministrationUuidId
	FROM `Core`.`RolePermission`
	WHERE `permissionUuid` IN(@systemAdministrationUuidId, @accessManagementUuidId);
UPDATE IGNORE `Core`.`RolePermission`
	SET `permissionUuid` = @coreAdministrationUuidId
	WHERE `permissionUuid` IN(@systemAdministrationUuidId, @accessManagementUuidId);
DELETE FROM `Core`.`RolePermissionLog`
	WHERE `permissionUuid` IN(@systemAdministrationUuidId, @accessManagementUuidId);
DELETE FROM `Core`.`RolePermissionLog`
	WHERE `roleUser` IN(
		SELECT `id` FROM `Core`.`RolePermission` WHERE `permissionUuid` IN(@systemAdministrationUuidId, @accessManagementUuidId)
	);
DELETE FROM `Core`.`RolePermission`
	WHERE `permissionUuid` IN(@systemAdministrationUuidId, @accessManagementUuidId);

INSERT INTO `Core`.`RolePermissionLog` (`roleUser`, `transaction`, `active`, `role`, `moduleInstanceBase`, `permissionUuid`)
	SELECT `id`, @transactionId, `active`, `role`, `moduleInstanceBase`, @coreOperationUuidId
	FROM `Core`.`RolePermission`
	WHERE `permissionUuid` = @superUserUuidId;
UPDATE IGNORE `Core`.`RolePermission`
	SET `permissionUuid` = @coreOperationUuidId
	WHERE `permissionUuid` = @superUserUuidId;
DELETE FROM `Core`.`RolePermissionLog`
	WHERE `permissionUuid` = @superUserUuidId;
DELETE FROM `Core`.`RolePermissionLog`
	WHERE `roleUser` IN(
		SELECT `id` FROM `Core`.`RolePermission` WHERE `permissionUuid` = @superUserUuidId
	);
DELETE FROM `Core`.`RolePermission`
	WHERE `permissionUuid` = @superUserUuidId;

-- END DML
