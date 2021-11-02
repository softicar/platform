package com.softicar.platform.emf.form.popup;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.table.row.IEmfTableRow;

@FunctionalInterface
public interface IEmfFormPopupContentFactory<R extends IEmfTableRow<R, ?>> {

	IDomElement create(R row);
}
