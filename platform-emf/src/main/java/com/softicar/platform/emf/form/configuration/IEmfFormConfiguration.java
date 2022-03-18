package com.softicar.platform.emf.form.configuration;

import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfFormConfiguration<R extends IEmfTableRow<R, ?>> {

	IEmfFormFactory<R> getFormFactory();
}
