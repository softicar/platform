package com.softicar.platform.workflow.module.demo;

import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;

public interface WorkflowDemoObjectPredicates {

	IEmfPredicate<AGWorkflowDemoObject> IS_WORKFLOW_STARTED =//
			new EmfPredicate<>(WorkflowI18n.IS_WORKFLOW_STARTED_QUESTION, AGWorkflowDemoObject::isWorkflowStarted);
}
