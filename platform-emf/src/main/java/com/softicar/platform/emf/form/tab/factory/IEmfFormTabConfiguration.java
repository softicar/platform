package com.softicar.platform.emf.form.tab.factory;

import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;

public interface IEmfFormTabConfiguration<R extends IEmfTableRow<R, ?>> {

	Collection<DomTab> createVisibleTabs(IEmfForm<R> form);

	boolean hasVisibleTabs(R tableRow);
}
