package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmfImportItem {

	private final IDbField<?, ?> field;
	private final List<EmfImportItem> constituents;
	private EmfImportItem parentItem;
	private Optional<Object> value = Optional.empty();

	public EmfImportItem(IDbField<?, ?> field) {

		this.field = field;
		this.constituents = new ArrayList<>();
	}

	public void addConstituent(EmfImportItem constituent) {

		constituents.add(constituent);
		constituent.parentItem = this;
	}

	public List<EmfImportItem> getConstituents() {

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

		this.value = Optional.of(value);
	}

	public <R> Object getValue() {

		return value.orElse(load());
	}

	private <R> Object load() {

		ISqlSelect<R> select = null;

		for (EmfImportItem constituent: constituents) {

			if (select == null) {
				select = CastUtils.cast(constituent.field.getTable().createSelect());
			}

			IDbField<R, Object> constituentField = CastUtils.cast(constituent.field);
			select = select.where(constituentField.isEqual(constituent.getValue()));
		}

		Object value = null;
		if (select != null) {
			value = CastUtils.cast(select.getOne());
			setValue(value);
		}
		return value;
	}

	@Override
	public String toString() {

//		return "EmfImportField [field=" + field + ", constituents=" + constituents + "]";
		return getName().toString();
	}

}
