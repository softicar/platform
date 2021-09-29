package com.softicar.platform.emf.data.table;

/**
 * Interface to customize an {@link IEmfDataTableRow}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@FunctionalInterface
public interface IEmfDataTableRowCustomizer<R> {

	/**
	 * Customizes the given table row.
	 *
	 * @param tableRow
	 *            the table row to modify (never <i>null</i>)
	 */
	void customizeRow(IEmfDataTableRow<R> tableRow);
}
