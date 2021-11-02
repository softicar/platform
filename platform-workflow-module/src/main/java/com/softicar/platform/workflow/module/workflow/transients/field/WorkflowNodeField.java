package com.softicar.platform.workflow.module.workflow.transients.field;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.runtime.transients.AbstractTransientOptionalField;
import com.softicar.platform.db.runtime.transients.IOptionalValueSetter;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class WorkflowNodeField<R extends IDbTableRow<R, ?>> extends AbstractTransientOptionalField<R, AGWorkflowNode> {

	private final IDbForeignField<R, AGWorkflowItem> workflowItemField;
	private final IDisplayString title;

	public WorkflowNodeField(IDbForeignField<R, AGWorkflowItem> workflowItemField, IDisplayString title) {

		this.workflowItemField = Objects.requireNonNull(workflowItemField);
		this.title = title;
	}

	@Override
	protected void loadValues(Set<R> rows, IOptionalValueSetter<R, AGWorkflowNode> setter) {

		var workflowItems = workflowItemField.prefetch(rows);
		AGWorkflowItem.WORKFLOW_NODE.prefetch(workflowItems);
		for (R row: rows) {
			Optional<AGWorkflowNode> workflowNode = Optional//
				.ofNullable(workflowItemField.getValue(row))
				.map(AGWorkflowItem::getWorkflowNode);
			setter.set(row, workflowNode);
		}
	}

	@Override
	public IDisplayString getTitle() {

		return title;
	}
}
