package com.softicar.platform.workflow.module.workflow.transients.field;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class WorkflowNodeField<R extends IDbTableRow<R, ?>> extends AbstractTransientObjectField<R, AGWorkflowNode> {

	private final IDbForeignField<R, AGWorkflowItem> workflowItemField;
	private final IDisplayString title;

	public WorkflowNodeField(IDbForeignField<R, AGWorkflowItem> workflowItemField) {

		this(workflowItemField, WorkflowI18n.WORKFLOW_NODE);
	}

	public WorkflowNodeField(IDbForeignField<R, AGWorkflowItem> workflowItemField, IDisplayString title) {

		super(AGWorkflowNode.class);

		this.workflowItemField = Objects.requireNonNull(workflowItemField);
		this.title = title;
	}

	@Override
	protected void loadValues(Set<R> rows, IValueSetter<R, AGWorkflowNode> setter) {

		var workflowItems = workflowItemField.prefetch(rows);
		AGWorkflowItem.WORKFLOW_NODE.prefetch(workflowItems);
		for (var row: rows) {
			var workflowNode = Optional//
				.ofNullable(workflowItemField.getValue(row))
				.map(AGWorkflowItem::getWorkflowNode)
				.orElse(null);
			setter.set(row, workflowNode);
		}
	}

	@Override
	public IDisplayString getTitle() {

		return title;
	}

	@Override
	protected AGWorkflowNode getDefaultValue() {

		return null;
	}
}
