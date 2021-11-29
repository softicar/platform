package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public interface WorkflowPredicates {

	IEmfPredicate<AGWorkflowVersion> WORKFLOW_VERSION_DRAFT = new EmfPredicate<>(//
		WorkflowI18n.DRAFT,
		AGWorkflowVersion::isDraft);

	// FIXME strange, not-draft implies finalized?
	IEmfPredicate<AGWorkflowVersion> WORKFLOW_VERSION_FINALIZED = WORKFLOW_VERSION_DRAFT.not();

	IEmfPredicate<AGWorkflow> WORKFLOW_VERSION_PRESENT = new EmfPredicate<>(//
		WorkflowI18n.WORKFLOW_VERSION_PRESENT,
		it -> !it.getWorkflowVersions().isEmpty());

	IEmfPredicate<AGWorkflowVersion> IS_CONSISTENT = new EmfPredicate<>(//
		WorkflowI18n.IS_CONSISTENT,
		AGWorkflowVersion::isConsistent);
}
