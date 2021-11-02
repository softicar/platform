package com.softicar.platform.emf.data.table.filter;

import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.i18n.IDisplayString;

public interface IEmfDataTableFilter<R> {

	IDisplayString getDisplayString();

	void addTo(IDataTableFilterList<R> filters);

	void reset();
}
