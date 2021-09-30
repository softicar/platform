package com.softicar.platform.workflow.module.workflow.node.precondition;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import java.util.Optional;

public class AGWorkflowNodePrecondition extends AGWorkflowNodePreconditionGenerated implements IEmfObject<AGWorkflowNodePrecondition> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getWorkflowNode().getName() + " ;; " + getFunction().toDisplay());
	}

	public Optional<IDisplayString> testAndReturnErrorMessage(AGWorkflowItem item) {

		IWorkflowPrecondition<?> precondition = getFunction().toReferencePoint(IWorkflowPrecondition.class);

		// FIXME: Find a better way to handle this cast
		if (precondition.test(CastUtils.cast(item.getEntityOrThrow()))) {
			return Optional.empty();
		} else {
			return Optional.of(precondition.getErrorMessage());
		}
	}

	public boolean test(AGWorkflowItem item) {

		return testAndReturnErrorMessage(item).isEmpty();
	}
}
