package com.softicar.platform.core.module.user.profile;

import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.localization.AGLocalization;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckboxGroup;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.emf.EmfI18n;

class UserProfileChangeLocalizationDiv extends DomDiv {

	private final DomCheckboxGroup<AGLocalization> localizationInput;

	public UserProfileChangeLocalizationDiv() {

		this.localizationInput = new LocalizationInput();
		var labelGrid = new DomLabelGrid().add(CoreI18n.LOCALIZATION, localizationInput);
		appendChild(labelGrid);
		appendChild(new DomActionBar(new SaveButton()));
	}

	private class LocalizationInput extends DomCheckboxGroup<AGLocalization> {

		public LocalizationInput() {

			AGLocalization.TABLE.loadAll().forEach(this::addOption);
			setValue(CurrentUser.get().getLocalization());
		}
	}

	private class SaveButton extends DomButton {

		public SaveButton() {

			setLabel(EmfI18n.SAVE);
			setClickCallback(this::handleClick);
		}

		public void handleClick() {

			CurrentUser.get().setLocalization(localizationInput.getValueOrThrow()).save();
			CurrentLocale.set(CurrentUser.get().getLocale());
			executeAlert(
				CoreI18n.YOUR_LOCALIZATION_HAS_CHANGED//
					.concatSentence(CoreI18n.PLEASE_PRESS_F5_TO_REFRESH));
		}
	}
}
