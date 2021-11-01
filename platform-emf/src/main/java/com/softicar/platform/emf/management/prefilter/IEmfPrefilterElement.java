package com.softicar.platform.emf.management.prefilter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;

public interface IEmfPrefilterElement<T extends IEmfTableRow<T, ?>> extends IDomElement {

	Collection<T> getFilteredItems();

	IDisplayString getCaption();

	void reset();
}
