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
