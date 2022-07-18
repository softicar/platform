package com.softicar.platform.emf.attribute.field.foreign.row;

import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * An {@link IEmfAttribute} representing an {@link IDbForeignRowField}.
 *
 * @author Oliver Richers
 */
public interface IEmfForeignRowAttribute<R extends IEmfTableRow<R, ?>, F extends IEmfTableRow<F, ?>> extends IEmfAttribute<R, F> {

	/**
	 * Returns the foreign {@link IEmfTable}.
	 *
	 * @return the foreign {@link IEmfTable} (never <i>null</i>)
	 */
	IEmfTable<F, ?, ?> getTargetTable();

	/**
	 * Returns the {@link IDbForeignRowField} that this
	 * {@link IEmfForeignRowAttribute} is based upon.
	 *
	 * @return the {@link IDbForeignRowField} (never <i>null</i>)
	 */
	IDbForeignRowField<R, F, ?> getForeignRowField();
}
