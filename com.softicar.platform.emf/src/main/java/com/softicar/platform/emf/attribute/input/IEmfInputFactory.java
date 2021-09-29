package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * Interface for the creation of new instanced of {@link IEmfInput}.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface IEmfInputFactory<R extends IEmfTableRow<R, ?>, V> {

	IEmfInput<V> createInput(R tableRow);
}
