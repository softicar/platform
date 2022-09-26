package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.emf.action.AbstractEmfButtonAction;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.node.action.WorkflowNodeActionPredicate;
import java.util.Objects;

public class WorkflowTransitionAction<R extends IWorkflowableObject<R>> extends AbstractEmfButtonAction<R> {

	private final AGWorkflowTransition transition;

	public WorkflowTransitionAction(AGWorkflowTransition transition) {

		this.transition = Objects.requireNonNull(transition);
	}

	@Override
	public IEmfPredicate<R> getPrecondition() {

		return new WorkflowNodeActionPredicate<>(transition.getSourceNode());
	}

	@Override
	public IEmfPermission<R> getRequiredPermission() {

		//TODO add permissions for all transitions
		return new WorkflowTransitionActionPermission<>(transition.getAllActiveWorkflowTransitionPermissions());
	}

	@Override
	public IResource getIcon() {

		return transition.getIcon();
	}

	@Override
	public IDisplayString getTitle() {

		return transition.toDisplayWithoutId();
	}

	@Override
	public void handleClick(IEmfFormBody<R> formBody) {

		formBody//
			.getTableRow()
			.getWorkflowItemAsOptional()
			.ifPresent(this::processWorkflowItem);

		formBody//
			.getRefreshBus()
			.setChanged(formBody.getTableRow());
	}

	private void processWorkflowItem(AGWorkflowItem item) {

		new WorkflowTransitionActionExecutor(item, transition, CurrentUser.get()).execute();
	}
}
