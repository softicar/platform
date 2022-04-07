package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;

public class EmfImportItem<R extends IEmfTableRow<R, P>, P> {

	private final IDbField<R, ?> field;
	private final List<EmfImportItem<R, ?>> constituents;
	private EmfImportItem<R, ?> parentItem;
	private Object value;

	public EmfImportItem(IDbField<R, ?> field) {

		this.field = field;
		this.constituents = new ArrayList<>();
	}

	public void addConstituent(EmfImportItem<R, ?> constituent) {

		constituents.add(constituent);
		constituent.parentItem = this;
	}

	public List<EmfImportItem<R, ?>> getConstituents() {

		return constituents;
	}

	public IDisplayString getName() {

		if (parentItem == null) {
			return field.getTitle();
		} else {
			return parentItem.getName().concat(":").concat(field.getTitle());
		}
	}

	public void setValue(Object value) {

		this.value = value;
	}

	public Object getValue() {

		if (value != null || constituents.isEmpty()) {
			return value;
		} else {
			return loadValue();
		}
	}

	private Object loadValue() {

		ISqlSelect<R> select = null;
		for (EmfImportItem<R, ?> constituent: constituents) {
			if (select == null) {
				select = constituent.field.getTable().createSelect();
			}
			IDbField<R, Object> constituentField = CastUtils.cast(constituent.field);
			select = select.where(constituentField.isEqual(constituent.getValue()));
		}
		return loadSetAndGetValue(select);
	}

	private Object loadSetAndGetValue(ISqlSelect<R> select) {

		this.value = select.getOne();
		return value;
	}

	@Override
	public String toString() {

		return getName().toString();
	}
}
