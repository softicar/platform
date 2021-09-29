package com.softicar.platform.db.runtime.entity;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.runtime.table.row.AbstractDbTableRow;
import java.util.Objects;

/**
 * Base implementation of {@link IDbEntity}.
 *
 * @param <R>
 *            the type of the table row
 * @param <P>
 *            the type of the primary key field
 * @author Oliver Richers
 */
public abstract class AbstractDbEntity<R extends AbstractDbEntity<R, P>, P> extends AbstractDbTableRow<R, P> implements IDbEntity<R, P> {

	@Override
	public int compareTo(IBasicItem item) {

		if (item == null || table().getValueClass().isInstance(item)) {
			return table().compare(getThis(), table().getValueClass().cast(item));
		} else {
			throw new SofticarDeveloperException(//
				"Cannot compare object of type %s to object of type %s.",
				table().getValueClass().getCanonicalName(),
				item.getClass().getCanonicalName());
		}
	}

	@Override
	public final boolean equals(Object other) {

		if (other instanceof Integer) {
			return Objects.equals(getId(), other);
		} else {
			return super.equals(other);
		}
	}
}
