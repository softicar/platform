package com.softicar.platform.emf.form;

import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.IEmfValidator;
import java.util.Collection;

public interface IEmfForm<R extends IEmfTableRow<R, ?>> {

	/**
	 * Returns the {@link IEmfFormFrame} containing this {@link IEmfForm}.
	 *
	 * @return the {@link IEmfFormFrame} (never <i>null</i>)
	 */
	IEmfFormFrame<R> getFrame();

	/**
	 * Returns the {@link IEmfTableRow} handled by this {@link IEmfForm}.
	 *
	 * @return the {@link IEmfTableRow} (never <i>null</i>)
	 */
	R getTableRow();

	/**
	 * Returns additional {@link IEmfValidator} objects that should be executed
	 * before the {@link IEmfTableRow} returned by {@link #getTableRow()} may be
	 * persisted.
	 *
	 * @return an {@link IEmfValidator} (never <i>null</i>)
	 */
	Collection<IEmfValidator<R>> getAdditionalValidators();
}
