package com.softicar.platform.dom.user;

import com.softicar.platform.dom.elements.popup.placement.strategy.IDomPopupPlacementStrategy;

/**
 * Represents DOM-specific user preferences.
 *
 * @author Alexander Schmidt
 */
public interface IDomPreferences {

	/**
	 * Returns the preferred {@link IDomPopupPlacementStrategy} to use for the
	 * current user.
	 * <p>
	 * The returned strategy is only applied if no other strategy is
	 * programmatically enforced.
	 *
	 * @return the preferred {@link IDomPopupPlacementStrategy} (never
	 *         <i>null</i>)
	 */
	IDomPopupPlacementStrategy getPreferredPopupPlacementStrategy();
}
