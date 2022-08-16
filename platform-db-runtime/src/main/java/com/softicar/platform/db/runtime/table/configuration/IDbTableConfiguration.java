package com.softicar.platform.db.runtime.table.configuration;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.getter.IDbTableRowGetterStrategy;

/**
 * Configuration of an {@link IDbTable}.
 *
 * @author Oliver Richers
 */
public interface IDbTableConfiguration<R, P> {

	IDbTableDataInitializer getDataInitializer();

	IDbTableRowGetterStrategy<R, P> getTableRowGetterStrategy();

	/**
	 * Returns the title of the table.
	 *
	 * @return the title of the table (never <i>null</i>)
	 */
	IDisplayString getTitle();

	/**
	 * Returns the pluralized title of the table.
	 *
	 * @return the pluralized title of the table (never <i>null</i>)
	 */
	IDisplayString getPluralTitle();
}
