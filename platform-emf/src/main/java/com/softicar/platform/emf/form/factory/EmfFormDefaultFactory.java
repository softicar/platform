package com.softicar.platform.emf.form.factory;

import com.softicar.platform.emf.form.EmfForm;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.IEmfFormFrame;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFormDefaultFactory<R extends IEmfTableRow<R, ?>> implements IEmfFormFactory<R> {

	@Override
	public IEmfForm<R> getForm(IEmfFormFrame<R> frame, R tableRow) {

		return new EmfForm<>(frame, tableRow);
	}
}
