package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.workflow.module.workflow.entity.table.IWorkflowTableReferencePoint;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

@SourceCodeReferencePointUuid("177fe233-45ed-4691-86ed-f6e543ebddcd")
public class WorkflowTestDocumentTableReferencePoint implements IWorkflowTableReferencePoint<WorkflowTestDocument> {

	@Override
	public IEmfTable<WorkflowTestDocument, ?, ?> getTable() {

		return WorkflowTestDocument.TABLE;
	}

	@Override
	public IDbForeignField<WorkflowTestDocument, AGWorkflowItem> getItemField() {

		return WorkflowTestDocument.ITEM;
	}
}
