package com.softicar.platform.workflow.module.workflow.transition.side.effect;

import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;

/**
 * A side effect that is executed when an {@link AGWorkflowTransition} is
 * executed.
 *
 * @author Oliver Richers
 */
public interface IWorkflowTransitionSideEffect<T extends IWorkflowableObject<T>> extends ISourceCodeReferencePoint {

	void execute(T object, AGWorkflowTransition transition);

	Class<T> getValueClass();

	default boolean isCompatible(IEmfTable<?, ?, ?> table) {

		return getValueClass().isAssignableFrom(table.getValueClass());
	}
}
