package com.softicar.platform.core.module.user.preferences;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.elements.popup.placement.strategy.IDomPopupPlacementStrategy;
import com.softicar.platform.dom.user.DomDefaultPreferences;
import com.softicar.platform.dom.user.IDomPreferences;
import java.util.Optional;

/**
 * Provides DOM-specific user preferences that are determined via
 * {@link UserPreferences}.
 *
 * @author Alexander Schmidt
 */
public class UserSpecificDomPreferences implements IDomPreferences {

	@Override
	public IDomPopupPlacementStrategy getPreferredPopupPlacementStrategy() {

		return Optional//
			.ofNullable(CurrentUser.get())
			.map(AGUser::getPreferences)
			.map(preferences -> preferences.preferredPopupPlacement)
			.map(UserPreferencesPreferredPopupPlacement::getStrategy)
			.orElse(new DomDefaultPreferences().getPreferredPopupPlacementStrategy());
	}
}
