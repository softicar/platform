package com.softicar.platform.workflow.module.workflow.transition.side.effect;

import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;

/**
 * A side effect that is executed when an {@link AGWorkflowTransition} is
 * executed.
 *
 * @author Oliver Richers
 */
public interface IWorkflowTransitionSideEffect<T extends IWorkflowableObject<T>> extends IEmfSourceCodeReferencePoint {

	void execute(T object, AGWorkflowTransition transition);

	Class<T> getValueClass();
}
