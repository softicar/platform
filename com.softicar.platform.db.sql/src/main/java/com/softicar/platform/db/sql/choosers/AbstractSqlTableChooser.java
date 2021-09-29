package com.softicar.platform.db.sql.choosers;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractSqlTableChooser<S extends ISqlSelectCoreAdapter> {

	private final S select;
	private final List<Integer> selectedTables;

	public AbstractSqlTableChooser(S select, PartType partType) {

		this.select = select;
		this.select.getCore().setPartType(partType);
		this.selectedTables = new ArrayList<>();
	}

	public AbstractSqlTableChooser(AbstractSqlTableChooser<S> other, int tableIndex) {

		this.select = other.select;
		this.selectedTables = other.selectedTables;
		this.selectedTables.add(tableIndex);
	}

	public final S addExpression(ISqlExpression<?> condition) {

		select.getCore().addExpression(selectedTables, condition);
		return select;
	}

	public final AbstractSqlTableChooser<S> addTable(int tableIndex) {

		selectedTables.add(tableIndex);
		return this;
	}
}
