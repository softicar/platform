package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRow;

/**
 * Utility methods for {@link IDbEnumTableRow} objects.
 *
 * @author Oliver Richers
 */
public class EmfEnumTableRows {

	/**
	 * Determines the {@link IDisplayString} for the given
	 * {@link IDbEnumTableRow}.
	 * <p>
	 * This first tries to call {@link IEntity#toDisplayWithoutId()}. If that
	 * does not work, {@link IDisplayable#toDisplay()} is tried. If this also
	 * fails, the {@link Enum#name()} is used without translating.
	 *
	 * @param row
	 *            the {@link IDbEnumTableRow} (never <i>null</i>)
	 * @return an {@link IDisplayString} (never <i>null</i>)
	 */
	public static IDisplayString toDisplay(IDbEnumTableRow<?, ?> row) {

		if (row instanceof IEntity) {
			return ((IEntity) row).toDisplayWithoutId();
		} else if (row instanceof IDisplayable) {
			return ((IDisplayable) row).toDisplay();
		} else {
			return IDisplayString.create(row.getEnum().name());
		}
	}
}
