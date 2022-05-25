package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class EmfImportColumn<R extends IEmfTableRow<R, P>, P> {

	private final IDbField<R, ?> field;
	private final List<EmfImportColumn<R, ?>> parentColumns;
	private final Stack<Object> value;
	private EmfImportColumn<R, ?> childColumn;

	public EmfImportColumn(IDbField<R, ?> field) {

		this.field = field;
		this.parentColumns = new ArrayList<>();
		this.value = new Stack<>();
	}

	public void addParentColumn(EmfImportColumn<R, ?> parentColumn) {

		parentColumns.add(parentColumn);
		parentColumn.childColumn = this;
	}

	public boolean isForeignKeyColumn() {

		return !parentColumns.isEmpty();
	}

	public boolean isGeneratedPrimaryKeyColumn() {

		IDbTableKey<R, ?> primaryKey = field.getTable().getPrimaryKey();
		return primaryKey.isGenerated() && primaryKey.getFields().contains(field);
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
			IEmfTable<?, ?, ?> emfTable = (IEmfTable<?, ?, ?>) field.getTable();
			return childColumn.getTitle().concat(":").concat("[" + emfTable.getPluralTitle() + "] " + field.getTitle());
		}
	}

	public EmfImportColumn<R, P> setValue(Object value) {

		this.value.clear();
		this.value.push(value);
		return this;
	}

	/**
	 * Returns the value if it has been set or loads it recursively by the
	 * values of its parents.
	 *
	 * @return the value
	 */
	public Object getOrLoadValue() {

		if (!value.isEmpty()) {
			return value.peek();
		} else if (!parentColumns.isEmpty()) {
			return loadValue();
		} else {
			throw new IllegalStateException("'" + getTitle() + "': The value has neither been set nor any parentColumns to load the value are available.");
		}
	}

	private Object loadValue() {

		ISqlSelect<R> select = null;
		boolean allParentsValuesAreNull = true;

		for (EmfImportColumn<R, ?> parentColumn: parentColumns) {
			IDbField<R, Object> parentColumnField = CastUtils.cast(parentColumn.field);
			if (select == null) {
				select = parentColumnField.getTable().createSelect();
			}
			Object parentValue = parentColumn.getOrLoadValue();
			if (parentValue != null) {
				allParentsValuesAreNull = false;
			}
			select = select.where(parentColumnField.isEqual(parentValue));
		}

		if (allParentsValuesAreNull) {
			return null;
		} else {
			return loadValue(select);
		}
	}

	private Object loadValue(ISqlSelect<R> select) {

		Object value = select.getOne();
		if (value == null) {
			throw new EmfImportColumnLoadException(this);
		} else {
			return value;
		}
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
