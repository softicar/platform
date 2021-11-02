package com.softicar.platform.emf.form;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfFormFrame<R extends IEmfTableRow<R, ?>> {

	void setTitle(IDisplayString title, IDisplayString subTitle);

	void closeFrame();

	void focusFrame();
}
