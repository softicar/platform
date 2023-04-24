package com.softicar.platform.dom.user;

import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupEventCoordinatesPlacementStrategy;
import com.softicar.platform.dom.elements.popup.placement.strategy.IDomPopupPlacementStrategy;

/**
 * Provides defaults for DOM-specific user preferences.
 *
 * @author Alexander Schmidt
 */
public class DomDefaultPreferences implements IDomPreferences {

	private static final DomDefaultPreferences INSTANCE = new DomDefaultPreferences();

	public static DomDefaultPreferences getInstance() {

		return INSTANCE;
	}

	@Override
	public IDomPopupPlacementStrategy getPreferredPopupPlacementStrategy() {

		return new DomPopupEventCoordinatesPlacementStrategy();
	}

	@Override
	public boolean enableDoubleClickOnTableRows() {

		return true;
	}
}
