package com.softicar.platform.workflow.module.workflow.node.precondition;

import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;

public interface IWorkflowPrecondition<R extends IWorkflowableObject<R>> extends IEmfPredicate<R>, ISourceCodeReferencePoint {

	/**
	 * Returns an error message that describes why this precondition was not
	 * fulfilled.
	 *
	 * @return the error message as an {@link IDisplayString} (never
	 *         <i>null</i>)
	 */
	IDisplayString getErrorMessage();

	@Override
	default IDisplayString toDisplay() {

		return getTitle();
	}
}
