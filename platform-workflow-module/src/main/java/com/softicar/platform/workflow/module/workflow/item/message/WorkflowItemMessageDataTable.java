package com.softicar.platform.workflow.module.workflow.item.message;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.WorkflowI18n;
import java.util.List;
import java.util.stream.Collectors;

public class WorkflowItemMessageDataTable extends AbstractInMemoryDataTable<WorkflowItemMessageRow> {

	private final boolean showTransitions;
	private final List<WorkflowItemMessageRow> rows;
	private final IDataTableColumn<WorkflowItemMessageRow, DayTime> atColumn;
	private final IDataTableColumn<WorkflowItemMessageRow, AGUser> byColumn;

	public WorkflowItemMessageDataTable(Boolean showTransitions, List<WorkflowItemMessageRow> rows) {

		this.showTransitions = showTransitions;
		this.rows = rows;
		this.byColumn = newColumn(AGUser.class)//
			.setGetter(WorkflowItemMessageRow::getCreatedBy)
			.setTitle(WorkflowI18n.BY)
			.addColumn();
		newColumn(String.class)//
			.setGetter(WorkflowItemMessageRow::getText)
			.setTitle(WorkflowI18n.TEXT)
			.addColumn();
		this.atColumn = newColumn(DayTime.class)//
			.setGetter(WorkflowItemMessageRow::getCreatedAt)
			.setTitle(WorkflowI18n.AT)
			.addColumn();
	}

	public IDataTableColumn<WorkflowItemMessageRow, AGUser> getByColumn() {

		return byColumn;
	}

	public IDataTableColumn<WorkflowItemMessageRow, DayTime> getAtColumn() {

		return atColumn;
	}

	@Override
	protected Iterable<WorkflowItemMessageRow> getTableRows() {

		return rows//
			.stream()
			.filter(row -> showTransitions? true : !row.isTransition())
			.collect(Collectors.toList());
	}
}
