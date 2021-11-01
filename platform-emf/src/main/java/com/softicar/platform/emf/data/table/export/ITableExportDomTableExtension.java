package com.softicar.platform.emf.data.table.export;

import com.softicar.platform.dom.elements.DomTable;

/**
 * Optional extension for the export of a {@link DomTable}.
 *
 * @author Oliver Richers
 */
public interface ITableExportDomTableExtension {

	/**
	 * Starts a database transaction to ensure consistent data snapshot during
	 * export execution.
	 * <p>
	 * After the export has been done, the transaction object will be closed.
	 * The closing should execute a roll-back of the transaction.
	 *
	 * @return a new database transaction object (never null)
	 */
	AutoCloseable startTransaction();
}
