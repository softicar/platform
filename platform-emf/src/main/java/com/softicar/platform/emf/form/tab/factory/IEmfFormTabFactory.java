package com.softicar.platform.emf.form.tab.factory;

import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfFormTabFactory<R extends IEmfTableRow<R, ?>> {

	boolean isVisible(R row);

	DomTab createTab(IEmfForm<R> form);
}
