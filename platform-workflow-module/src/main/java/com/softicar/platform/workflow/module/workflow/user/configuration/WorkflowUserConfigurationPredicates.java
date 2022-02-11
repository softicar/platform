package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;

public interface WorkflowUserConfigurationPredicates {

	IEmfPredicate<AGWorkflowUserConfiguration> SUBSTITUTE_DEFINED = new EmfPredicate<>(//
		WorkflowI18n.SUBSTITUTE_DEFINED,
		it -> it.getSubstitute() != null);
}
