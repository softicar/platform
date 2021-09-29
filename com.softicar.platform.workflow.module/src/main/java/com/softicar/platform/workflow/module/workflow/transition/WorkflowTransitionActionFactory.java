package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.action.factory.IEmfPrimaryActionFactory;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.entity.table.IWorkflowTableReferencePoint;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import java.util.ArrayList;
import java.util.Collection;

public class WorkflowTransitionActionFactory<R extends IWorkflowableObject<R>> implements IEmfPrimaryActionFactory<R> {

	private final AGUuid tableUuid;

	public WorkflowTransitionActionFactory(Class<? extends IWorkflowTableReferencePoint<?>> referencePointClass) {

		this.tableUuid = AGUuid.getOrCreate(referencePointClass);
	}

	@Override
	public Collection<IEmfPrimaryAction<R>> apply(R workflowableObject) {

		Collection<IEmfPrimaryAction<R>> set = new ArrayList<>();
		if (workflowableObject.isActive()) {
			AGWorkflowTransition
				.createSelect()
				.where(AGWorkflowTransition.AUTO_TRANSITION.isFalse())
				.join(AGWorkflowTransition.WORKFLOW_VERSION)
				.join(AGWorkflowVersion.WORKFLOW)
				.where(AGWorkflow.ENTITY_TABLE.equal(tableUuid))
				.forEach(transition -> set.add(new WorkflowTransitionAction<>(transition)));
		}
		return set;
	}
}
