package com.softicar.platform.emf.form.popup;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;

public interface IEmfFormPopupConfiguration<R extends IEmfTableRow<R, ?>> {

	Collection<IDomElement> createAdditionalContent(R tableRow);
}
