-- migration to platform-46

-- PLAT-1389

ALTER TABLE `Workflow`.`WorkflowTransitionExecution`
	DROP FOREIGN KEY `WorkflowTransitionExecution_ibfk_1`,
	DROP FOREIGN KEY `WorkflowTransitionExecution_ibfk_2`,
	DROP FOREIGN KEY `WorkflowTransitionExecution_ibfk_3`;

USE Workflow;
RENAME TABLE `WorkflowTransitionExecution` TO `WorkflowTaskExecution`;

ALTER TABLE `Workflow`.`WorkflowTaskExecution`
	ADD CONSTRAINT `WorkflowTaskExecution_ibfk_1` FOREIGN KEY (`workflowTask`) REFERENCES `Workflow`.`WorkflowTask` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
	ADD CONSTRAINT `WorkflowTaskExecution_ibfk_2` FOREIGN KEY (`workflowTransition`) REFERENCES `Workflow`.`WorkflowTransition` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
	ADD CONSTRAINT `WorkflowTaskExecution_ibfk_3` FOREIGN KEY (`transaction`) REFERENCES `Core`.`Transaction` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
