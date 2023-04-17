package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public interface WorkflowPredicates {

	IEmfPredicate<AGWorkflow> WORKFLOW_VERSION_PRESENT = new EmfPredicate<>(//
		WorkflowI18n.WORKFLOW_VERSION_PRESENT,
		it -> !it.getWorkflowVersions().isEmpty());

	IEmfPredicate<AGWorkflowVersion> IS_CURRENT_VERSION = new EmfPredicate<>(//
		WorkflowI18n.IS_CURRENT_VERSION,
		AGWorkflowVersion::isCurrentVersion);

	IEmfPredicate<AGWorkflowVersion> NOT_IS_CURRENT_VERSION = IS_CURRENT_VERSION.not();

	IEmfPredicate<AGWorkflow> HAS_CURRENT_VERSION = new EmfPredicate<>(//
		WorkflowI18n.HAS_CURRENT_VERSION,
		workflow -> workflow.getCurrentVersion() != null);
}
