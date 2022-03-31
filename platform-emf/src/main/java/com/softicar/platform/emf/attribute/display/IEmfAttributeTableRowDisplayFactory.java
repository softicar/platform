package com.softicar.platform.emf.attribute.display;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

@FunctionalInterface
public interface IEmfAttributeTableRowDisplayFactory<R extends IEmfTableRow<R, ?>> {

	/**
	 * Creates an {@link IDomElement} to display information from the given
	 * {@link IEmfTableRow}.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} that provides the information to be
	 *            displayed (never <i>null</i>)
	 * @return an {@link IDomElement} to display information from the given
	 *         {@link IEmfTableRow} (never <i>null</i>)
	 */
	IDomElement createDisplay(R tableRow);

	/**
	 * Creates an {@link IDomElement} to display information from the given
	 * {@link IEmfTableRow} in a tabular UI element, e.g.
	 * {@link EmfManagementDiv}.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} that provides the information to be
	 *            displayed (never <i>null</i>)
	 * @return an {@link IDomElement} to display information from the given
	 *         {@link IEmfTableRow} (never <i>null</i>)
	 */
	default IDomElement createTabularDisplay(R tableRow) {

		return createDisplay(tableRow);
	}
}
