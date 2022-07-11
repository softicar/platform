package com.softicar.platform.workflow.module.test;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.workflow.module.workflow.entity.table.IWorkflowTableReferencePoint;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

@SourceCodeReferencePointUuid("efed2a60-94a7-4861-8e71-52684f62f731")
public class WorkflowTestObjectTableReferencePoint implements IWorkflowTableReferencePoint<WorkflowTestObject> {

	@Override
	public IEmfTable<WorkflowTestObject, ?, ?> getTable() {

		return WorkflowTestObject.TABLE;
	}

	@Override
	public IDbForeignField<WorkflowTestObject, AGWorkflowItem> getItemField() {

		return WorkflowTestObject.ITEM;
	}
}
