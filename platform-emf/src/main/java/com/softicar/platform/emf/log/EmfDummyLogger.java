package com.softicar.platform.emf.log;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EmfDummyLogger<R extends IEmfTableRow<R, ?>> implements IEmfChangeLogger<R> {

	private final Set<IDbField<R, ?>> fields;

	public EmfDummyLogger() {

		this.fields = new HashSet<>();
	}

	public EmfDummyLogger<R> addField(IDbField<R, ?> field) {

		fields.add(field);
		return this;
	}

	public Set<IDbField<R, ?>> getFields() {

		return fields;
	}

	@Override
	public void logChange(Collection<R> tableRows) {

		// nothing to do by design
	}
}
