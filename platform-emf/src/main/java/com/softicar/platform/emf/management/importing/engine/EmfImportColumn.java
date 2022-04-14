package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmfImportColumn<R extends IEmfTableRow<R, P>, P> {

	private final IDbField<R, ?> field;
	private final List<EmfImportColumn<R, ?>> foreignKeyColumns;
	private EmfImportColumn<R, ?> sourceColumn;
	private Object value;

	public EmfImportColumn(IDbField<R, ?> field) {

		this.field = field;
		this.foreignKeyColumns = new ArrayList<>();
	}

	public void addForeignKeyColumn(EmfImportColumn<R, ?> foreignKeyColumn) {

		foreignKeyColumns.add(foreignKeyColumn);
		foreignKeyColumn.sourceColumn = this;
	}

	public boolean isForeignKeyColumn() {

		return !foreignKeyColumns.isEmpty();
	}

	public IDbField<R, ?> getField() {

		return field;
	}

	public List<EmfImportColumn<R, ?>> getForeignKeyColumns() {

		return foreignKeyColumns;
	}

	public IDisplayString getTitle() {

		if (sourceColumn == null) {
			return field.getTitle();
		} else {
			return sourceColumn.getTitle().concat(":").concat(field.getTitle());
		}
	}

	public void setValue(Object value) {

		this.value = value;
	}

	public Object getValue() {

		if (value != null || foreignKeyColumns.isEmpty()) {
			return value;
		} else {
			return loadValue();
		}
	}

	private Object loadValue() {

		ISqlSelect<R> select = null;
		for (EmfImportColumn<R, ?> foreignKeyColumn: foreignKeyColumns) {
			IDbField<R, Object> foreignKeyColumnField = CastUtils.cast(foreignKeyColumn.field);
			if (select == null) {
				select = foreignKeyColumnField.getTable().createSelect();
			} else {
				select = select.where(foreignKeyColumnField.isEqual(foreignKeyColumn.getValue()));
			}
		}
		return loadSetAndGetValue(select);
	}

	private Object loadSetAndGetValue(ISqlSelect<R> select) {

		this.value = select.getOne();
		return value;
	}

	@Override
	public String toString() {

		return getTitle().toString();
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof EmfImportColumn) {

			EmfImportColumn<R, ?> otherEmfImportColumn = CastUtils.cast(object);
			// TODO it has to ignore sourceColumn because otherwise it goes eternal loop!
			return Objects.equals(foreignKeyColumns, otherEmfImportColumn.foreignKeyColumns);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(foreignKeyColumns);
	}
}
