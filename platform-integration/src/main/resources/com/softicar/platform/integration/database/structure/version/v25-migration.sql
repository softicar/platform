-- migration from platform-24 to platform-25

ALTER TABLE `Core`.`ReplicationChecksum`
	CHANGE COLUMN `lower_boundary` `lower_boundary` TEXT DEFAULT NULL,
	CHANGE COLUMN `upper_boundary` `upper_boundary` TEXT DEFAULT NULL;


SELECT 'Core.BufferedEmailLog' INTO @logTable;
SELECT 'Core.BufferedEmail' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'bufferedEmail' INTO @constraintColumn;
SELECT 'BufferedEmailLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.BufferedEmailLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'BufferedEmailLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.CoreModuleInstanceLog' INTO @logTable;
SELECT 'Core.CoreModuleInstance' INTO @targetTable;
SELECT 'base' INTO @targetColumn;
SELECT 'coreModuleInstance' INTO @constraintColumn;
SELECT 'CoreModuleInstanceLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.CoreModuleInstanceLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'CoreModuleInstanceLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT ', @constraint, ';'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT ', @constraint, ' FOREIGN KEY (', @constraintColumn, ') REFERENCES ', @targetTable, ' (`id`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.LocalizationLog' INTO @logTable;
SELECT 'Core.Localization' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'user' INTO @constraintColumn;
SELECT 'LocalizationLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.LocalizationLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'LocalizationLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.MaintenanceWindowLog' INTO @logTable;
SELECT 'Core.MaintenanceWindow' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'maintenanceWindow' INTO @constraintColumn;
SELECT 'MaintenanceWindowLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.MaintenanceWindowLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'MaintenanceWindowLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.ModuleInstanceBaseLog' INTO @logTable;
SELECT 'Core.ModuleInstanceBase' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'moduleInstanceBase' INTO @constraintColumn;
SELECT 'ModuleInstanceBaseLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.ModuleInstanceBaseLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'ModuleInstanceBaseLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.ModuleInstancePermissionAssignmentLog' INTO @logTable;
SELECT 'Core.ModuleInstancePermissionAssignment' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'assignment' INTO @constraintColumn;
SELECT 'ModuleInstancePermissionAssignmentLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.ModuleInstancePermissionAssignmentLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'ModuleInstancePermissionAssignmentLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.ModulePanicReceiverLog' INTO @logTable;
SELECT 'Core.ModulePanicReceiver' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'modulePanicReceiver' INTO @constraintColumn;
SELECT 'ModulePanicReceiverLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.ModulePanicReceiverLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'ModulePanicReceiverLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.PasswordPolicyLog' INTO @logTable;
SELECT 'Core.PasswordPolicy' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'passwordPolicy' INTO @constraintColumn;
SELECT 'PasswordPolicyLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.PasswordPolicyLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'PasswordPolicyLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.ProgramExecutionLog' INTO @logTable;
SELECT 'Core.ProgramExecution' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'programExecution' INTO @constraintColumn;
SELECT 'ProgramExecutionLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.ProgramExecutionLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'ProgramExecutionLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.ProgramLog' INTO @logTable;
SELECT 'Core.Program' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'program' INTO @constraintColumn;
SELECT 'ProgramLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.ProgramLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'ProgramLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.RoleLog' INTO @logTable;
SELECT 'Core.Role' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'role' INTO @constraintColumn;
SELECT 'RoleLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.RoleLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'RoleLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.RolePermissionLog' INTO @logTable;
SELECT 'Core.RolePermission' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'roleUser' INTO @constraintColumn;
SELECT 'RolePermissionLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.RolePermissionLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'RolePermissionLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.RoleUserLog' INTO @logTable;
SELECT 'Core.RoleUser' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'roleUser' INTO @constraintColumn;
SELECT 'RoleUserLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.RoleUserLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'RoleUserLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.ScheduledProgramExecutionLog' INTO @logTable;
SELECT 'Core.ScheduledProgramExecution' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'scheduledProgramExecution' INTO @constraintColumn;
SELECT 'ScheduledProgramExecutionLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.ScheduledProgramExecutionLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'ScheduledProgramExecutionLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.ServerLog' INTO @logTable;
SELECT 'Core.Server' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'server' INTO @constraintColumn;
SELECT 'ServerLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.ServerLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'ServerLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.StoredFileServerLog' INTO @logTable;
SELECT 'Core.StoredFileServer' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'storedFileServer' INTO @constraintColumn;
SELECT 'StoredFileServerLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.StoredFileServerLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'StoredFileServerLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.SystemEventLog' INTO @logTable;
SELECT 'Core.SystemEvent' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'systemEvent' INTO @constraintColumn;
SELECT 'SystemEventLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.SystemEventLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'SystemEventLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.UserAllowedIpRuleLog' INTO @logTable;
SELECT 'Core.UserAllowedIpRule' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'userAllowedIpRule' INTO @constraintColumn;
SELECT 'UserAllowedIpRuleLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.UserAllowedIpRuleLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'UserAllowedIpRuleLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Core.UserLog' INTO @logTable;
SELECT 'Core.User' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'user' INTO @constraintColumn;
SELECT 'UserLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Core.UserLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'UserLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowDemoObjectApproverLog' INTO @logTable;
SELECT 'Workflow.WorkflowDemoObjectApprover' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'approver' INTO @constraintColumn;
SELECT 'WorkflowDemoObjectApproverLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowDemoObjectApproverLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowDemoObjectApproverLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowDemoObjectLog' INTO @logTable;
SELECT 'Workflow.WorkflowDemoObject' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'workflowDemoObject' INTO @constraintColumn;
SELECT 'WorkflowDemoObjectLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowDemoObjectLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowDemoObjectLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowIconLog' INTO @logTable;
SELECT 'Workflow.WorkflowIcon' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'workflowIcon' INTO @constraintColumn;
SELECT 'WorkflowIconLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowIconLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowIconLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowItemLog' INTO @logTable;
SELECT 'Workflow.WorkflowItem' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'workflowItem' INTO @constraintColumn;
SELECT 'WorkflowItemLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowItemLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowItemLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowLog' INTO @logTable;
SELECT 'Workflow.Workflow' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'workflow' INTO @constraintColumn;
SELECT 'WorkflowLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowNodeActionLog' INTO @logTable;
SELECT 'Workflow.WorkflowNodeAction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'workflowNodeAction' INTO @constraintColumn;
SELECT 'WorkflowNodeActionLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowNodeActionLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowNodeActionLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowNodeActionPermissionLog' INTO @logTable;
SELECT 'Workflow.WorkflowNodeActionPermission' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'workflowNodeActionPermission' INTO @constraintColumn;
SELECT 'WorkflowNodeActionPermissionLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowNodeActionPermissionLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowNodeActionPermissionLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowNodeLog' INTO @logTable;
SELECT 'Workflow.WorkflowNode' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'workflowNode' INTO @constraintColumn;
SELECT 'WorkflowNodeLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowNodeLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowNodeLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowNodePreconditionLog' INTO @logTable;
SELECT 'Workflow.WorkflowNodePrecondition' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'workflowNodePrecondition' INTO @constraintColumn;
SELECT 'WorkflowNodePreconditionLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowNodePreconditionLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowNodePreconditionLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowTaskDelegationLog' INTO @logTable;
SELECT 'Workflow.WorkflowTaskDelegation' INTO @targetTable;
SELECT 'workflowTask' INTO @targetColumn;
SELECT 'workflowTaskDelegation' INTO @constraintColumn;
SELECT 'WorkflowTaskDelegationLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowTaskDelegationLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowTaskDelegationLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowTaskLog' INTO @logTable;
SELECT 'Workflow.WorkflowTask' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'workflowTask' INTO @constraintColumn;
SELECT 'WorkflowTaskLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowTaskLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowTaskLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowTransitionLog' INTO @logTable;
SELECT 'Workflow.WorkflowTransition' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'workflowTransition' INTO @constraintColumn;
SELECT 'WorkflowTransitionLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowTransitionLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowTransitionLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowTransitionPermissionLog' INTO @logTable;
SELECT 'Workflow.WorkflowTransitionPermission' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transitionPermission' INTO @constraintColumn;
SELECT 'WorkflowTransitionPermissionLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowTransitionPermissionLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowTransitionPermissionLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowUserConfigurationLog' INTO @logTable;
SELECT 'Workflow.WorkflowUserConfiguration' INTO @targetTable;
SELECT 'user' INTO @targetColumn;
SELECT 'workflowUserConfiguration' INTO @constraintColumn;
SELECT 'WorkflowUserConfigurationLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowUserConfigurationLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowUserConfigurationLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SELECT 'Workflow.WorkflowVersionLog' INTO @logTable;
SELECT 'Workflow.WorkflowVersion' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'workflowVersion' INTO @constraintColumn;
SELECT 'WorkflowVersionLog_ibfk_1' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SELECT 'Workflow.WorkflowVersionLog' INTO @logTable;
SELECT 'Core.Transaction' INTO @targetTable;
SELECT 'id' INTO @targetColumn;
SELECT 'transaction' INTO @constraintColumn;
SELECT 'WorkflowVersionLog_ibfk_2' INTO @constraint;
SET @runString = CONCAT('ALTER TABLE ', @logTable, ' DROP CONSTRAINT `', @constraint, '`;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET @runString = CONCAT('ALTER TABLE ', @logTable, ' ADD CONSTRAINT `', @constraint, '` FOREIGN KEY (`', @constraintColumn, '`) REFERENCES ', @targetTable, ' (`', @targetColumn, '`) ON DELETE CASCADE ON UPDATE CASCADE;'); PREPARE stmt FROM @runString; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- ---------------------------------------------------------------

ALTER TABLE `Core`.`Uuid` DROP COLUMN `uuidBytes`;

-- ---------------------------------------------------------------

ALTER TABLE `Core`.`Server`
	ADD COLUMN `connectorUuid` INT DEFAULT NULL,
	ADD COLUMN `connectorConfiguration` MEDIUMTEXT NOT NULL DEFAULT '',
	ADD COLUMN `connectorData` MEDIUMTEXT NOT NULL DEFAULT '',
	ADD KEY `connectorUuid` (`connectorUuid`),
	ADD CONSTRAINT `Server_ibfk_1` FOREIGN KEY (`connectorUuid`) REFERENCES `Core`.`Uuid` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `Core`.`ServerLog`
	ADD COLUMN `connectorUuid` INT DEFAULT NULL,
	ADD COLUMN `connectorConfiguration` MEDIUMTEXT DEFAULT NULL,
	ADD KEY `connectorUuid` (`connectorUuid`),
	ADD CONSTRAINT `ServerLog_ibfk_3` FOREIGN KEY (`connectorUuid`) REFERENCES `Core`.`Uuid` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
