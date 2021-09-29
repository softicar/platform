package com.softicar.platform.emf.attribute.field.foreign.row;

import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * A factory for {@link IEmfForeignRowAttribute}.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface IEmfForeignRowAttributeFactory<F extends IEmfTableRow<F, FP>, FP> {

	<R extends IEmfTableRow<R, ?>> EmfForeignRowAttribute<R, F> create(IDbForeignRowField<R, F, FP> field);
}
