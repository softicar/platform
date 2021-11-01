package com.softicar.platform.workflow.module.workflow.task.delegation;

import com.softicar.platform.emf.validation.AbstractEmfValidator;
import com.softicar.platform.workflow.module.WorkflowI18n;
import java.util.Optional;

public class WorkflowTaskDelegationValidator extends AbstractEmfValidator<AGWorkflowTaskDelegation> {

	@Override
	protected void validate() {

		if (Optional.ofNullable(tableRow.getTargetUser()).map(it -> it.isSystemUser()).orElse(false)) {
			addError(AGWorkflowTaskDelegation.TARGET_USER, WorkflowI18n.TARGET_USER_IS_SYSTEM_USER);
		}
	}
}
