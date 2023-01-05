package com.softicar.platform.core.module.user.navigation;

import com.softicar.platform.core.module.user.UserPreferences;
import com.softicar.platform.emf.attribute.field.bool.EmfBooleanInput;

public class RecursivelyCollapseFoldersInput extends EmfBooleanInput {

	private final UserPreferences preferences;

	public RecursivelyCollapseFoldersInput(UserPreferences preferences) {

		super(preferences.recursivelyCollapseFolders);
		this.preferences = preferences;
		addChangeCallback(this::handleChange);
	}

	private void handleChange() {

		if (preferences.automaticallyCollapseFolders) {
			setValue(true);
			setDisabled(true);
		} else {
			setDisabled(false);
		}
	}
}
