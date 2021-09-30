package com.softicar.platform.db.sql.selects;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import java.util.ArrayList;

class AbstractSqlSelectChooser {

	private final SqlSelectBase select;
	private final ArrayList<Integer> selectedTables;

	public AbstractSqlSelectChooser(SqlSelectBase select) {

		this.select = select;
		this.selectedTables = new ArrayList<>();
	}

	public AbstractSqlSelectChooser(AbstractSqlSelectChooser other, int tableIndex) {

		this.select = other.select;
		this.selectedTables = other.selectedTables;
		this.selectedTables.add(tableIndex);
	}

	public final <S extends SqlSelectBase> S _addExpression(S newSelect, ISqlExpression<?> expression) {

		newSelect._init(select);
		newSelect.getCore().addExpression(selectedTables, expression);
		return newSelect;
	}
}
