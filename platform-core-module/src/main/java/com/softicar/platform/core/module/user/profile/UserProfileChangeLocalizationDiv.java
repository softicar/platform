package com.softicar.platform.core.module.user.profile;

import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.editor.EmfAttributesDiv;

class UserProfileChangeLocalizationDiv extends DomDiv {

	private final EmfAttributesDiv<AGUser> attributesDiv;

	public UserProfileChangeLocalizationDiv() {

		this.attributesDiv = new EmfAttributesDiv<>(CurrentUser.get(), true);
		attributesDiv.addAttribute(AGUser.LOCALIZATION);

		appendChild(attributesDiv);
		appendChild(new DomActionBar(new SaveButton()));
	}

	private class SaveButton extends DomButton {

		public SaveButton() {

			setLabel(EmfI18n.SAVE);
			setClickCallback(this::handleClick);
		}

		public void handleClick() {

			if (attributesDiv.tryToApplyValidateAndSave()) {
				CurrentLocale.set(CurrentUser.get().getLocale());
				executeAlert(
					CoreI18n.YOUR_LOCALIZATION_HAS_CHANGED//
						.concatSentence(CoreI18n.PLEASE_PRESS_F5_TO_REFRESH));
			}
		}
	}
}
