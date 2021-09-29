package com.softicar.platform.common.container.comparator;

import com.softicar.platform.common.container.CommonContainerI18n;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;

/**
 * Simple enum for the two possible order directions, ascending and descending.
 *
 * @author Oliver Richers
 */
public enum OrderDirection implements IDisplayable {

	ASCENDING(1, CommonContainerI18n.ASCENDING),
	DESCENDING(-1, CommonContainerI18n.DESCENDING);

	private final int sign;
	private final IDisplayString displayString;

	private OrderDirection(int sign, IDisplayString displayString) {

		this.sign = sign;
		this.displayString = displayString;
	}

	@Override
	public IDisplayString toDisplay() {

		return displayString;
	}

	/**
	 * Returns the reverse order direction of this direction.
	 *
	 * @return reverse direction (never null)
	 */
	public OrderDirection getReversed() {

		return this == ASCENDING? DESCENDING : ASCENDING;
	}

	/**
	 * Returns the comparison sign of this direction.
	 * <p>
	 * The sign is <i>1</i> for {@link #ASCENDING} and <i>-1</i> for
	 * {@link #DESCENDING}
	 *
	 * @return the comparison sign
	 */
	public int getSign() {

		return sign;
	}

	/**
	 * Returns <i>true</i> if this is {@link OrderDirection#ASCENDING}.
	 *
	 * @return <i>true</i> if this is {@link OrderDirection#ASCENDING};
	 *         <i>false</i> otherwise.
	 */
	public boolean isAscending() {

		return this == ASCENDING;
	}

	/**
	 * Returns <i>true</i> if this is {@link OrderDirection#DESCENDING}.
	 *
	 * @return <i>true</i> if this is {@link OrderDirection#DESCENDING};
	 *         <i>false</i> otherwise.
	 */
	public boolean isDescending() {

		return this == DESCENDING;
	}
}
