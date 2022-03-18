package com.softicar.platform.emf.form.configuration;

import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.IEmfFormFrame;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfFormFactory<R extends IEmfTableRow<R, ?>> {

	IEmfForm<R> getForm(IEmfFormFrame<R> frame, R tableRow);
}
