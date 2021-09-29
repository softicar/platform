package com.softicar.platform.common.container.data.table;

import com.softicar.platform.common.container.CommonContainerI18n;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;

/**
 * List all boolean operators for {@link IDataTableFilterList}.
 *
 * @author Oliver Richers
 */
public enum DataTableFilterListOperator implements IDisplayable {

	AND(CommonContainerI18n.AND),
	OR(CommonContainerI18n.OR);

	private final IDisplayString title;

	private DataTableFilterListOperator(IDisplayString title) {

		this.title = title;
	}

	@Override
	public IDisplayString toDisplay() {

		return title;
	}
}
