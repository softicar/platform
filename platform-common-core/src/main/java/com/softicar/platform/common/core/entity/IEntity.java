package com.softicar.platform.common.core.entity;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.item.IBasicItem;

/**
 * Represents an {@link IBasicItem} with a title.
 *
 * @author Oliver Richers
 */
public interface IEntity extends IBasicItem, IDisplayable {

	/**
	 * Converts this {@link IEntity} into an {@link IDisplayable}.
	 * <p>
	 * The {@link IDisplayString} shall not contain the ID of the
	 * {@link IEntity}.
	 *
	 * @return the {@link IDisplayString} without ID (never null)
	 */
	IDisplayString toDisplayWithoutId();

	@Override
	default IDisplayString toDisplay() {

		return toDisplayWithoutId().concat(" [" + getId() + "]");
	}
}
