package com.softicar.platform.emf.action;

import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfCommonAction<R extends IEmfTableRow<R, ?>> extends IEmfAction<R> {

	void handleClick(IEmfFormBody<R> formBody);
}
