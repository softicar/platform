package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.transients.ITransientField;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.entity.table.IWorkflowTableReferencePoint;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import java.util.Collection;
import java.util.Optional;

public class AGWorkflow extends AGWorkflowGenerated implements IEmfObject<AGWorkflow> {

	public static final ITransientField<AGWorkflow, Integer> ITEM_COUNT_FIELD = new WorkflowItemCountField();
	public static final ITransientField<AGWorkflow, Integer> ITEM_COUNT_IN_INACTIVE_VERSIONS_FIELD = new WorkflowItemCountInInavticeVersionsField();

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}

	public AGWorkflowVersion getCurrentVersionOrThrow() {

		return Optional//
			.ofNullable(getCurrentVersion())
			.orElseThrow(() -> new SofticarUserException(WorkflowI18n.NO_CURRENT_WORKFLOW_VERSION_FOUND));
	}

	public Collection<AGWorkflowVersion> getWorkflowVersions() {

		return AGWorkflowVersion//
			.createSelect()
			.where(AGWorkflowVersion.WORKFLOW.isEqual(this))
			.list();
	}

	@SuppressWarnings("rawtypes")
	public Optional<IWorkflowTableReferencePoint> getTableReferencePoint() {

		return SourceCodeReferencePoints//
			.getReferencePoint(getEntityTable().getUuid(), IWorkflowTableReferencePoint.class);
	}

	public IWorkflowTableReferencePoint<?> getTableReferencePointOrThrow() {

		return SourceCodeReferencePoints//
			.getReferencePointOrThrow(getEntityTable().getUuid(), IWorkflowTableReferencePoint.class);
	}

	public void startWorkflow(IWorkflowableObject<?> object) {

		new WorkflowStarter(this).startWorkflow(object);
	}
}
