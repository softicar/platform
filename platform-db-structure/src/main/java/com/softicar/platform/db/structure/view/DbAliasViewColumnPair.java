package com.softicar.platform.db.structure.view;

import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Optional;

public class DbAliasViewColumnPair {

	private final String viewColumn;
	private final String tableColumn;

	public DbAliasViewColumnPair(String viewColumn, String tableColumn) {

		this.viewColumn = viewColumn;
		this.tableColumn = tableColumn;
	}

	public String getViewColumn() {

		return viewColumn;
	}

	public String getTableColumn() {

		return tableColumn;
	}

	public Optional<IDbColumnStructure> getTableColumnStructure(IDbTableStructure tableStructure) {

		return tableStructure.getColumnByPhysicalName(tableColumn);
	}

	@Override
	public String toString() {

		return String.format("%s=%s", viewColumn, tableColumn);
	}
}
