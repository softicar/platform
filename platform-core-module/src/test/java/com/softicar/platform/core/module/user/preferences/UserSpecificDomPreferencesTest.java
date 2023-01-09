package com.softicar.platform.core.module.user.preferences;

import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.UserPreferencesPreferredPopupPlacement;
import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupContextCenterPlacementStrategy;
import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupEventCoordinatesPlacementStrategy;
import org.junit.Test;

public class UserSpecificDomPreferencesTest extends AbstractCoreTest {

	private final UserSpecificDomPreferences preferences;

	public UserSpecificDomPreferencesTest() {

		this.preferences = new UserSpecificDomPreferences();
	}

	@Test
	public void testGetPreferredPopupPlacementStrategy() {

		CurrentUser.get().updatePreferences(preferences -> preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED);
		var strategy = preferences.getPreferredPopupPlacementStrategy();
		assertTrue(strategy.getClass().equals(DomPopupContextCenterPlacementStrategy.class));
	}

	@Test
	public void testGetPreferredPopupPlacementStrategyWithDefaults() {

		var strategy = preferences.getPreferredPopupPlacementStrategy();
		assertTrue(strategy.getClass().equals(DomPopupEventCoordinatesPlacementStrategy.class));
	}
}
