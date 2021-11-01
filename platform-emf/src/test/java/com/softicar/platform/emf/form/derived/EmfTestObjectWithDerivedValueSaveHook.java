package com.softicar.platform.emf.form.derived;

import com.softicar.platform.emf.table.listener.IEmfSaveHook;
import java.util.Collection;

public class EmfTestObjectWithDerivedValueSaveHook implements IEmfSaveHook<EmfTestObjectWithDerivedValue> {

	@Override
	public void beforeSave(Collection<EmfTestObjectWithDerivedValue> tableRows) {

		tableRows.forEach(this::deriveValue);
	}

	private void deriveValue(EmfTestObjectWithDerivedValue tableRow) {

		if (tableRow.getValue() != null) {
			tableRow.setDerivedValue(tableRow.getValue() * tableRow.getValue());
		}
	}
}
