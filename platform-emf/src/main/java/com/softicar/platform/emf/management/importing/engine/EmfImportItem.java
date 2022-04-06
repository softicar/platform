package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmfImportItem<R> {

	private final IDbField<R, ?> field;
	private final List<EmfImportItem<R>> constituents;
	private EmfImportItem<R> parentItem;
	private Optional<String> value = Optional.empty();

	public EmfImportItem(IDbField<R, ?> field) {

		this.field = field;
		this.constituents = new ArrayList<>();
	}

	public IDbField<R, ?> getField() {

		return field;
	}

	public void addConstituent(EmfImportItem<R> constituent) {

		constituents.add(constituent);
		constituent.parentItem = this;
	}

	public List<EmfImportItem<R>> getConstituents() {

		return constituents;
	}

	public IDisplayString getName() {

		if (parentItem == null) {
			return field.getTitle();
		} else {
			return parentItem.getName().concat(":").concat(field.getTitle());
		}
	}

	public void setValue(String value) {

		this.value = Optional.of(value);
	}

	public String getValue() {

		if (value.isEmpty()) {
			ISqlSelect<R> select = null;

			for (EmfImportItem<R> constituent: constituents) {
				if (select == null) {
					select = constituent.getField().getTable().createSelect();
				}

				IDbField<R, ?> constituentField = constituent.getField();
				String constituentValue = constituent.getValue();

				select = select.where(((IDbField<R, String>) constituentField).isEqual(constituentValue));

//				select = select.where(constituent.getField().equal(constituent.getValue()));

				select.getOne();

			}
		} else {
			return value.get();
		}

		return "";
	}

	@Override
	public String toString() {

//		return "EmfImportField [field=" + field + ", constituents=" + constituents + "]";
		return getName().toString();
	}

}
