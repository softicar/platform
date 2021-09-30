package com.softicar.platform.emf.attribute.display;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

@FunctionalInterface
public interface IEmfAttributeFieldValueDisplayFactory<R, V> {

	/**
	 * Creates an {@link IDomElement} to display the value contained in the
	 * given {@link IDbField} of the given {@link IEmfTableRow}.
	 *
	 * @param field
	 *            the {@link IDbField} that contains the information to be
	 *            displayed
	 * @param tableRow
	 *            the {@link IEmfTableRow} that provides the information to be
	 *            displayed
	 * @return an {@link IDomElement} to display the value contained in the
	 *         given {@link IDbField} of the given {@link IEmfTableRow} (may be
	 *         null)
	 */
	IDomElement createDisplay(Optional<IDbField<R, V>> field, R tableRow);
}
