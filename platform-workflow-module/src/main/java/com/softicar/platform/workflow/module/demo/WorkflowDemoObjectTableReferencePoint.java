package com.softicar.platform.workflow.module.demo;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.workflow.module.workflow.entity.table.IWorkflowTableReferencePoint;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

@SourceCodeReferencePointUuid("99b9d13d-2425-4313-ac77-9df7648aa99d")
public class WorkflowDemoObjectTableReferencePoint implements IWorkflowTableReferencePoint<AGWorkflowDemoObject> {

	@Override
	public AGWorkflowDemoObjectTable getTable() {

		return AGWorkflowDemoObject.TABLE;
	}

	@Override
	public IDbForeignField<AGWorkflowDemoObject, AGWorkflowItem> getItemField() {

		return AGWorkflowDemoObject.WORKFLOW_ITEM;
	}
}
