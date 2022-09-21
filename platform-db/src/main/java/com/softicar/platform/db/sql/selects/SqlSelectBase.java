package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;
import java.util.ArrayList;
import java.util.List;

abstract class SqlSelectBase implements ISqlSelectCoreAdapter {

	SqlSelectCore core;

	public SqlSelectBase() {

		// nothing to do
	}

	public SqlSelectBase(SqlSelectCore core) {

		this.core = core;
	}

	public final void log() {

		Log.finfo(core._build(0, 0).getText());
	}

	@Override
	public final void setCore(SqlSelectCore core) {

		this.core = core;
	}

	@Override
	public final SqlSelectCore getCore() {

		return core;
	}

	public abstract class SelectChooserBase {

		private final List<Integer> selectedTables;

		public SelectChooserBase() {

			getCore().setPartType(PartType.SELECT);
			this.selectedTables = new ArrayList<>();
		}

		public SelectChooserBase(SelectChooserBase other, int tableIndex) {

			this.selectedTables = other.selectedTables;
			this.selectedTables.add(tableIndex);
		}

		public final <S extends ISqlSelectCoreAdapter> S addExpression(S newSelect, ISqlExpression<?> expression) {

			newSelect.setCore(getCore());
			setCore(null);

			newSelect.getCore().addExpression(selectedTables, expression);
			return newSelect;
		}
	}

	public final <T, S extends ISqlSelectCoreAdapter> S _join(S newSelect, ISqlTable<T> table, JoinType joinType) {

		// get core
		newSelect.setCore(getCore());
		newSelect.getCore().join(table, joinType);
		setCore(null);
		return newSelect;
	}

	public final void _init(SqlSelectBase other) {

		this.core = other.core;
		other.core = null;
	}
}
