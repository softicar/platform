package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;

public interface WorkflowTransitionPredicates {

	IEmfPredicate<AGWorkflowTransition> AUTO_TRANSITION = new EmfPredicate<>(//
		WorkflowI18n.AUTO_TRANSITION,
		AGWorkflowTransition::isAutoTransition);
}
