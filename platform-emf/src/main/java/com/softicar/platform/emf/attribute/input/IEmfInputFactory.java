package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * Interface for the creation of new instanced of {@link IEmfInput}.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface IEmfInputFactory<R extends IEmfTableRow<R, ?>, V> {

	/**
	 * Creates a new {@link IEmfInput} for the given {@link IEmfTableRow}.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} (never <i>null</i>)
	 * @return the new {@link IEmfInput} (never <i>null</i>)
	 */
	IEmfInput<V> createInput(R tableRow);
}
