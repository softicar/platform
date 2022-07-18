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

	IEmfTable<F, ?, ?> getTargetTable();

	IDbForeignRowField<R, F, ?> getForeignRowField();
}
