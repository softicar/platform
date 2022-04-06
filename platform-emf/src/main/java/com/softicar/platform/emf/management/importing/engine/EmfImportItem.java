package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbField;
import java.util.ArrayList;
import java.util.List;

public class EmfImportItem {

	private final IDbField<?, ?> field;
	private final List<EmfImportItem> constituents;
	private EmfImportItem parentItem;

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

	@Override
	public String toString() {

//		return "EmfImportField [field=" + field + ", constituents=" + constituents + "]";
		return getName().toString();
	}

}
