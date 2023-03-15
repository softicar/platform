-- migration from platform-33 to platform-38

-- EXP-2731

ALTER TABLE `Workflow`.`WorkflowUserConfiguration`
	MODIFY COLUMN `emailNotificationsForNewTasks` BOOL NOT NULL DEFAULT '0';
