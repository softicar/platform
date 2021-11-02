package com.softicar.platform.emf.form.section.factory;

import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.EmfFormSectionDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

@FunctionalInterface
public interface IEmfFormSectionFactory<R extends IEmfTableRow<R, ?>> {

	EmfFormSectionDiv<R> createSection(IEmfFormBody<R> body);
}
