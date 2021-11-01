package com.softicar.platform.emf.attribute.display;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.table.row.IEmfTableRow;

@FunctionalInterface
public interface IEmfAttributeTableRowDisplayFactory<R extends IEmfTableRow<R, ?>> {

	/**
	 * Creates an {@link IDomElement} to display information from the given
	 * {@link IEmfTableRow}.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} that provides the information to be
	 *            displayed
	 * @return an {@link IDomElement} to display information from the given
	 *         {@link IEmfTableRow} (may be null)
	 */
	IDomElement createDisplay(R tableRow);

	default IDomElement createTabularDisplay(R tableRow) {

		return createDisplay(tableRow);
	}
}
