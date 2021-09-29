package com.softicar.platform.db.runtime.enums;

import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import java.util.Arrays;

/**
 * Basic implementation of {@link IDbEnumTableRow}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDbEnumTableRow<R extends AbstractDbEnumTableRow<R, E>, E extends IDbEnumTableRowEnum<E, R>> extends AbstractDbObject<R>
		implements IDbEnumTableRow<R, E> {

	@Override
	public E getEnum() {

		return table().getEnumById(getId());
	}

	public boolean is(E enumRow) {

		if (getId() == null || enumRow.getId() == null) {
			return false;
		}
		return getId().equals(enumRow.getId());
	}

	@SuppressWarnings("unchecked")
	public boolean isIn(E...enumRows) {

		return Arrays.asList(enumRows).stream().anyMatch(this::is);
	}
}
