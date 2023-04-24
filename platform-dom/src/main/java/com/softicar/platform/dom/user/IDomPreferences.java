package com.softicar.platform.dom.user;

import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.popup.DomPopup;
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

	/**
	 * Enables double click events on table rows.
	 * <p>
	 * For example, with this enabled, double clicking a {@link DomRow} may open
	 * a {@link DomPopup} representing the record. Every {@link DomRow} needs to
	 * implement the feature manually, but should obey this preference flag.
	 * <p>
	 * <b>Caveat</b>: enabling double click events prevents the user from
	 * selecting text using double click. Users may prefer to disable this
	 * feature.
	 *
	 * @return <i>true</i> to enable double click; <i>false</i> to disable
	 *         double click
	 */
	boolean enableDoubleClickOnTableRows();
}
