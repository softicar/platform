package com.softicar.platform.emf.mapper;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

public class EmfTableRowFieldMapper<S, T> implements IEmfTableRowMapper<S, T> {

	private final ISqlField<S, T> field;

	public EmfTableRowFieldMapper(ISqlField<S, T> field) {

		this.field = field;
	}

	@Override
	public IDisplayString getTitle() {

		ISqlTable<S> table = field.getTable();
		if (table instanceof IEmfTable) {
			// TODO remove or improve cast (i54622)
			return getTitleFromEntityTable((IEmfTable<?, ?, ?>) table);
		} else {
			return getTitleFromPlainField();
		}
	}

	@Override
	public Optional<T> apply(S entity) {

		return Optional.ofNullable(field.getValue(entity));
	}

	@SuppressWarnings("unchecked")
	private <R extends IEmfTableRow<R, ?>> IDisplayString getTitleFromEntityTable(IEmfTable<R, ?, ?> table) {

		return EmfI18n.ARG1_OF_ARG2.toDisplay(table.getAttribute((ISqlField<R, ?>) field).getTitle(), table.getTitle());
	}

	private IDisplayString getTitleFromPlainField() {

		return EmfI18n.ARG1_OF_ARG2.toDisplay(field.getName(), field.getTable().getValueClass().getSimpleName());
	}
}
