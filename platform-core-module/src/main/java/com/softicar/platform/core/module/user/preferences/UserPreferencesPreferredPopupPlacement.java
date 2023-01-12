package com.softicar.platform.core.module.user.preferences;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupContextCenterPlacementStrategy;
import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupContextTopCenterPlacementStrategy;
import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupContextTopLeftPlacementStrategy;
import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupEventCoordinatesPlacementStrategy;
import com.softicar.platform.dom.elements.popup.placement.strategy.IDomPopupPlacementStrategy;
import java.util.Objects;

/**
 * Enumerates preferred {@link DomPopup} placements that can be selected as a
 * user preferences.
 * <p>
 * <b>WARNING:</b> Used in {@link UserPreferences}. Renaming constants in this
 * enum will partially invalidate stored user preferences.
 *
 * @author Alexander Schmidt
 */
public enum UserPreferencesPreferredPopupPlacement implements IDisplayable {

	AT_EVENT_COORDINATES(DomI18n.AT_CLICK_POSITION, new DomPopupEventCoordinatesPlacementStrategy()),
	CENTERED(DomI18n.CENTERED, new DomPopupContextCenterPlacementStrategy()),
	TOP_CENTERED(DomI18n.TOP_CENTERED, new DomPopupContextTopCenterPlacementStrategy()),
	TOP_LEFT(DomI18n.TOP_LEFT, new DomPopupContextTopLeftPlacementStrategy()),
	//
	;

	private final IDisplayString displayName;
	private final IDomPopupPlacementStrategy strategy;

	private UserPreferencesPreferredPopupPlacement(IDisplayString displayName, IDomPopupPlacementStrategy strategy) {

		this.displayName = Objects.requireNonNull(displayName);
		this.strategy = Objects.requireNonNull(strategy);
	}

	@Override
	public IDisplayString toDisplay() {

		return displayName;
	}

	/**
	 * Returns the associated {@link IDomPopupPlacementStrategy}.
	 *
	 * @return the {@link IDomPopupPlacementStrategy} (never <i>null</i>)
	 */
	public IDomPopupPlacementStrategy getStrategy() {

		return strategy;
	}
}
