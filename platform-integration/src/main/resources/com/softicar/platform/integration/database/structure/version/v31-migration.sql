-- migration from platform-30 to platform-31

-- -------- PLAT-1232 --------

ALTER TABLE `Workflow`.`WorkflowTransition`
	ADD COLUMN `commentRequired` BOOL NOT NULL DEFAULT '0';

ALTER TABLE `Workflow`.`WorkflowTransitionLog`
	ADD COLUMN `commentRequired` BOOL DEFAULT NULL;
