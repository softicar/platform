package com.softicar.platform.workflow.module.workflow.item.message;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.item.message.severity.AGWorkflowMessageSeverityEnum;
import java.util.List;
import java.util.stream.Collectors;

public class WorkflowItemMessageDataTable extends AbstractInMemoryDataTable<WorkflowItemMessageRow> {

	private final boolean verbose;
	private final List<WorkflowItemMessageRow> rows;
	private final IDataTableColumn<WorkflowItemMessageRow, Integer> indexColumn;
	private final IDataTableColumn<WorkflowItemMessageRow, DayTime> atColumn;
	private final IDataTableColumn<WorkflowItemMessageRow, AGUser> byColumn;

	public WorkflowItemMessageDataTable(boolean verbose, List<WorkflowItemMessageRow> rows) {

		this.verbose = verbose;
		this.rows = rows;
		this.indexColumn = newColumn(Integer.class)//
			.setGetter(WorkflowItemMessageRow::getIndex)
			.setTitle(WorkflowI18n.INDEX)
			.addColumn();
		newColumn(String.class)//
			.setGetter(WorkflowItemMessageRow::getNodeName)
			.setTitle(WorkflowI18n.WORKFLOW_NODE)
			.addColumn();
		newColumn(String.class)//
			.setGetter(WorkflowItemMessageRow::getText)
			.setTitle(WorkflowI18n.TEXT)
			.addColumn();
		this.byColumn = newColumn(AGUser.class)//
			.setGetter(WorkflowItemMessageRow::getCreatedBy)
			.setTitle(WorkflowI18n.BY)
			.addColumn();
		this.atColumn = newColumn(DayTime.class)//
			.setGetter(WorkflowItemMessageRow::getCreatedAt)
			.setTitle(WorkflowI18n.AT)
			.addColumn();
	}

	@Override
	public DataTableIdentifier getIdentifier() {

		return new DataTableIdentifier("b3e48cc3-7f2c-41a4-87f4-2d882112d0f6");
	}

	@Override
	protected Iterable<WorkflowItemMessageRow> getTableRows() {

		return rows//
			.stream()
			.filter(row -> verbose || row.getSeverity().isMoreImportantThan(AGWorkflowMessageSeverityEnum.VERBOSE))
			.collect(Collectors.toList());
	}

	public IDataTableColumn<WorkflowItemMessageRow, Integer> getIndexColumn() {

		return indexColumn;
	}

	public IDataTableColumn<WorkflowItemMessageRow, AGUser> getByColumn() {

		return byColumn;
	}

	public IDataTableColumn<WorkflowItemMessageRow, DayTime> getAtColumn() {

		return atColumn;
	}
}
