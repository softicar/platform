package com.softicar.platform.emf.deactivation;

import com.softicar.platform.db.sql.statement.ISqlSelectOrJoin;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.editor.EmfDeactivateAction;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfTableRowDeactivationStrategy<R extends IEmfTableRow<R, ?>> {

	/**
	 * Checks if the {@link IEmfTable} of the given {@link IEmfTableRow}
	 * supports deactivation, most commonly achieved trough an active field.
	 *
	 * @return <i>true</i> if the table supports deactivation, <i>false</i>
	 *         otherwise (never null)
	 */
	boolean isDeactivationSupported();

	/**
	 * Checks if the given {@link IEmfTableRow} can be deactivated by calling
	 * {@link #setActive(IEmfTableRow, boolean)}.
	 * <p>
	 * For {@link IEmfTable}, this determines if the {@link EmfDeactivateAction}
	 * is appended.
	 *
	 * @param tableRow
	 *            the table row to check
	 * @return <i>true</i> if this {@link IEmfTableRow} can be deactivated,
	 *         <i>false</i> otherwise (never null)
	 */
	boolean isDeactivationAvailable(R tableRow);

	/**
	 * Checks if the given {@link IEmfTableRow} is active.
	 *
	 * @param tableRow
	 *            the table row to check
	 * @return if deactivation is supported, returns the value of the active
	 *         field of the given {@link IEmfTableRow}, <i>true</i> otherwise.
	 */
	boolean isActive(R tableRow);

	<V> boolean isActiveAttribute(IEmfAttribute<R, V> attribute);

	void addWhereActive(ISqlSelectOrJoin<?, R, ?> select);

	void setActive(R tableRow, boolean active);
}
