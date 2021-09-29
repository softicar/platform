package com.softicar.platform.emf.table.configuration;

import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfAttributeDefaultValueSet<R extends IEmfTableRow<R, ?>, S> {

	void applyTo(R row, S scope);
}
