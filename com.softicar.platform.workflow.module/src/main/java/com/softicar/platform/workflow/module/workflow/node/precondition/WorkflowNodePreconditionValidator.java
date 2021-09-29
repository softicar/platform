package com.softicar.platform.workflow.module.workflow.node.precondition;

import com.softicar.platform.emf.validation.AbstractEmfValidator;
import com.softicar.platform.workflow.module.WorkflowI18n;

public class WorkflowNodePreconditionValidator extends AbstractEmfValidator<AGWorkflowNodePrecondition> {

	@Override
	protected void validate() {

		// FIXME this is completely awkward; this test tries to prevent modifications to a non-draft workflow through validation?!
		if (tableRow.impermanent() || tableRow.dataChanged()) {
			if (!tableRow.getWorkflowNode().getWorkflowVersion().isDraft()) {
				addError(AGWorkflowNodePrecondition.WORKFLOW_NODE, WorkflowI18n.WORKFLOW_VERSION_FINALIZED);
			}
		}
	}
}
