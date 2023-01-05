package com.softicar.platform.core.module.user.profile;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.UserPreferences;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.field.bool.EmfBooleanInput;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.ArrayList;
import java.util.List;

class UserProfilePreferencesDiv extends DomDiv {

	private final UserPreferences preferences;

	public UserProfilePreferencesDiv() {

		this.preferences = CurrentUser.get().getPreferences();

		appendChild(
			new PreferencesElement()//
				.addInput(CoreI18n.AUTOMATICALLY_COLLAPSE_FOLDERS, new AutomaticallyCollapseFoldersCheckbox())
				.addInput(CoreI18n.RECURSIVELY_COLLAPSE_FOLDERS, new RecursivelyCollapseFoldersCheckbox())
				.refreshInputConstraints());
		appendChild(
			new DomActionBar(
				new DomButton()//
					.addMarker(CoreTestMarker.USER_PREFERENCES_SAVE_BUTTON)
					.setLabel(EmfI18n.SAVE)
					.setClickCallback(this::save)));
	}

	private void save() {

		CurrentUser.get().savePreferences(preferences);
		executeAlert(CoreI18n.PREFERENCES_SAVED);
	}

	private class PreferencesElement extends DomLabelGrid {

		private final List<IEmfInput<?>> inputs;

		public PreferencesElement() {

			this.inputs = new ArrayList<>();
		}

		public PreferencesElement addInput(IDisplayString labelText, IEmfInput<?> input) {

			add(labelText, input);
			input.addChangeCallback(this::refreshInputConstraints);
			inputs.add(input);
			return this;
		}

		public PreferencesElement refreshInputConstraints() {

			inputs.forEach(IEmfInput::refreshInputConstraints);
			return this;
		}
	}

	private class AutomaticallyCollapseFoldersCheckbox extends EmfBooleanInput {

		public AutomaticallyCollapseFoldersCheckbox() {

			super(preferences.automaticallyCollapseFolders);
			addMarker(CoreTestMarker.USER_PREFERENCES_AUTOMATICALLY_COLLAPSE_FOLDERS_CHECKBOX);
		}

		@Override
		public void refreshInputConstraints() {

			preferences.automaticallyCollapseFolders = getValueOrThrow();
		}
	}

	private class RecursivelyCollapseFoldersCheckbox extends EmfBooleanInput {

		public RecursivelyCollapseFoldersCheckbox() {

			super(preferences.recursivelyCollapseFolders);
			addMarker(CoreTestMarker.USER_PREFERENCES_RECURSIVELY_COLLAPSE_FOLDERS_CHECKBOX);
		}

		@Override
		public void refreshInputConstraints() {

			if (preferences.automaticallyCollapseFolders) {
				setValue(true);
				setDisabled(true);
			} else {
				setDisabled(false);
			}

			preferences.recursivelyCollapseFolders = getValueOrThrow();
		}
	}
}
