package com.softicar.platform.workflow.module.workflow.node;

import com.softicar.platform.emf.validation.AbstractEmfValidator;
import com.softicar.platform.workflow.module.WorkflowI18n;

public class WorkflowNodeValidator extends AbstractEmfValidator<AGWorkflowNode> {

	@Override
	protected void validate() {

		// FIXME this is completely awkward; this test tries to prevent modifications to a non-draft workflow through validation?!
		if (tableRow.impermanent() || tableRow.dataChanged()) {
			if (!tableRow.getWorkflowVersion().isDraft()) {
				addError(AGWorkflowNode.WORKFLOW_VERSION, WorkflowI18n.WORKFLOW_VERSION_FINALIZED);
			}
		}
	}
}
