package com.softicar.platform.emf.form.section;

import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;

public interface IEmfFormSectionConfiguration<R extends IEmfTableRow<R, ?>> {

	Collection<EmfFormSectionDiv<R>> createAvailableSections(IEmfFormBody<R> body);
}
