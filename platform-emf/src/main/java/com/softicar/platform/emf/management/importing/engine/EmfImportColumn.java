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
	private final List<EmfImportColumn<R, ?>> parentColumns;
	private EmfImportColumn<R, ?> childColumn;
	private Object value;

	public EmfImportColumn(IDbField<R, ?> field) {

		this.field = field;
		this.parentColumns = new ArrayList<>();
	}

	public void addParentColumn(EmfImportColumn<R, ?> parentColumn) {

		parentColumns.add(parentColumn);
		parentColumn.childColumn = this;
	}

	public boolean isForeignKeyColumn() {

		return !parentColumns.isEmpty();
	}

	public IDbField<R, ?> getField() {

		return field;
	}

	public List<EmfImportColumn<R, ?>> getParentColumns() {

		return parentColumns;
	}

	public IDisplayString getTitle() {

		if (childColumn == null) {
			return field.getTitle();
		} else {
			return childColumn.getTitle().concat(":").concat(field.getTitle());
		}
	}

	public EmfImportColumn<R, P> setValue(Object value) {

		this.value = value;
		return this;
	}

	public Object getValue() {

		if (value != null || parentColumns.isEmpty()) {
			return value;
		} else {
			return loadValue();
		}
	}

	private Object loadValue() {

		ISqlSelect<R> select = null;
		for (EmfImportColumn<R, ?> parentColumn: parentColumns) {
			IDbField<R, Object> parentColumnField = CastUtils.cast(parentColumn.field);
			if (select == null) {
				select = parentColumnField.getTable().createSelect();
			}
			select = select.where(parentColumnField.isEqual(parentColumn.getValue()));
		}
		return loadSetAndGetValue(select);
	}

	private Object loadSetAndGetValue(ISqlSelect<R> select) {

		value = select.getOne();

		if (value == null) {
			// TODO throw SofticarUserException
		}

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
			return Objects.equals(parentColumns, otherEmfImportColumn.parentColumns);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(parentColumns);
	}
}
