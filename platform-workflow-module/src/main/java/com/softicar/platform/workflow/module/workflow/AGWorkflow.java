package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.entity.table.IWorkflowTableReferencePoint;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.task.WorkflowTaskManager;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import java.util.Collection;

public class AGWorkflow extends AGWorkflowGenerated implements IEmfObject<AGWorkflow> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}

	public Collection<AGWorkflowVersion> getWorkflowVersions() {

		return AGWorkflowVersion//
			.createSelect()
			.where(AGWorkflowVersion.WORKFLOW.equal(this))
			.list();
	}

	public IWorkflowTableReferencePoint<?> getTableReferencePointOrThrow() {

		return SourceCodeReferencePoints//
			.getReferencePointOrThrow(getEntityTable().getUuid(), IWorkflowTableReferencePoint.class);
	}

	public void startWorkflow(IWorkflowableObject<?> object) {

		if (object.getWorkflowItem() == null) {
			AGWorkflowItem item = new AGWorkflowItem() //
				.setWorkflow(this)
				.setWorkflowNode(getCurrentVersion().getRootNode())
				.save();

			object.setWorkflowItem(item);
			object.save();
		} else {
			object//
				.getWorkflowItem()
				.setWorkflow(this)
				.setWorkflowNode(getCurrentVersion().getRootNode())
				.save();
		}

		new WorkflowTaskManager(object.getWorkflowItem()).setNextNodeAndGenerateTasks(getCurrentVersion().getRootNode());
	}
}
