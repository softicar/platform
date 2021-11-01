package com.softicar.platform.emf.data.table.export.precondition;

import com.softicar.platform.common.container.map.list.ListTreeMap;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResult.Level;
import java.util.List;

public class TableExportPreconditionResultContainer {

	private final ListTreeMap<Level, TableExportPreconditionResult> map = new ListTreeMap<>();

	public void add(TableExportPreconditionResult preconditionResult) {

		this.map.addToList(preconditionResult.getLevel(), preconditionResult);
	}

	public List<TableExportPreconditionResult> getAllByLevel(Level level) {

		return this.map.getList(level);
	}
}
