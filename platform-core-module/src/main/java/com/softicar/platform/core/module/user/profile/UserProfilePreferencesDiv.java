package com.softicar.platform.core.module.user.profile;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.preferences.UserPreferences;
import com.softicar.platform.core.module.user.preferences.UserPreferencesNavigationFolderCollapseMode;
import com.softicar.platform.core.module.user.preferences.UserPreferencesPreferredPopupPlacement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.checkbox.DomCheckboxGroup;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.input.IDomValueInput;
import com.softicar.platform.emf.EmfI18n;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class UserProfilePreferencesDiv extends DomDiv {

	private final UserPreferences preferences;

	public UserProfilePreferencesDiv() {

		this.preferences = CurrentUser.get().getPreferences();

		appendChild(
			new PreferencesElement()//
				.addInput(CoreI18n.COLLAPSE_NAVIGATION_FOLDERS, new NavigationFolderCollapseModeInput())
				.addInput(CoreI18n.PREFERRED_POPUP_PLACEMENT, new PreferredPopupPlacementInput())
				.addInput(CoreI18n.DOUBLE_CLICK_ON_TABLE_ROWS_OPENS_POPUP, new DoubleClickOnTableRowInput())
				.refreshAllInputs());
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

		private final List<IRefreshable> inputs;

		public PreferencesElement() {

			this.inputs = new ArrayList<>();
		}

		public <T extends IDomValueInput<?> & IRefreshable> PreferencesElement addInput(IDisplayString labelText, T input) {

			add(labelText, input);
			input.addChangeCallback(this::refreshAllInputs);
			inputs.add(input);
			return this;
		}

		public PreferencesElement refreshAllInputs() {

			inputs.forEach(IRefreshable::refresh);
			return this;
		}
	}

	private class NavigationFolderCollapseModeInput extends DomCheckboxGroup<UserPreferencesNavigationFolderCollapseMode> implements IRefreshable {

		public NavigationFolderCollapseModeInput() {

			Arrays.asList(UserPreferencesNavigationFolderCollapseMode.values()).forEach(this::addOption);
			setValue(preferences.navigationFolderCollapseMode);
			addMarker(CoreTestMarker.USER_PREFERENCES_NAVIGATION_FOLDER_COLLAPSE_MODE);
		}

		@Override
		public void refresh() {

			preferences.navigationFolderCollapseMode = getValueOrNull();
		}
	}

	private class PreferredPopupPlacementInput extends DomCheckboxGroup<UserPreferencesPreferredPopupPlacement> implements IRefreshable {

		public PreferredPopupPlacementInput() {

			Arrays.asList(UserPreferencesPreferredPopupPlacement.values()).forEach(this::addOption);
			setValue(preferences.preferredPopupPlacement);
			addMarker(CoreTestMarker.USER_PREFERENCES_PREFERRED_POPUP_PLACEMENT);
		}

		@Override
		public void refresh() {

			preferences.preferredPopupPlacement = getValueOrNull();
		}
	}

	private class DoubleClickOnTableRowInput extends DomCheckbox implements IRefreshable {

		public DoubleClickOnTableRowInput() {

			super(preferences.enableDoubleClickOnTableRows);
			setTitle(CoreI18n.ENABLING_THIS_PREVENT_TEXT_SELECTION_WITH_DOUBLE_CLICK);
		}

		@Override
		public void refresh() {

			preferences.enableDoubleClickOnTableRows = isChecked();
		}
	}
}
