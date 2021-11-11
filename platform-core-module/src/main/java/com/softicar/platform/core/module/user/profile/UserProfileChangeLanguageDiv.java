package com.softicar.platform.core.module.user.profile;

import com.softicar.platform.common.core.i18n.CurrentLanguage;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.language.AGCoreLanguage;
import com.softicar.platform.core.module.language.AGCoreLanguageEnum;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;

class UserProfileChangeLanguageDiv extends DomDiv {

	private final LanguageTextDiv languageTextDiv;

	public UserProfileChangeLanguageDiv() {

		this.languageTextDiv = new LanguageTextDiv();

		appendChild(languageTextDiv);
		DomActionBar actionBar = appendChild(new DomActionBar());
		for (AGCoreLanguageEnum languageEnum: AGCoreLanguageEnum.values()) {
			actionBar.appendChild(new LanguageSelectButton(languageEnum));
		}
	}

	private static class LanguageTextDiv extends DomDiv {

		public LanguageTextDiv() {

			refresh();
		}

		private void refresh() {

			removeChildren();
			appendText(CoreI18n.YOUR_CURRENT_LANGUAGE_IS_ARG1.toDisplay(CurrentUser.get().getPreferredLanguage().toDisplayWithoutId()));
			appendNewChild(DomElementTag.BR);
			appendText(CoreI18n.TO_SELECT_A_DIFFERENT_LANGUAGE_CLICK_ON_THE_RESPECTIVE_FLAG_BELOW);
		}
	}

	private class LanguageSelectButton extends DomButton {

		private final AGCoreLanguage language;

		public LanguageSelectButton(AGCoreLanguageEnum languageEnum) {

			this.language = languageEnum.getRecord();

			setIconAndTitle();
			setClickCallback(this::handleClick);
		}

		public void handleClick() {

			language.getLanguageEnum().ifPresent(CurrentLanguage::set);

			AGUser user = CurrentUser.get();
			user.setPreferredLanguage(language);
			user.save();

			languageTextDiv.refresh();
			executeAlert(
				CoreI18n.YOUR_LANGUAGE_HAS_BEEN_CHANGED_TO_ARG1//
					.toDisplay(language.toDisplay())
					.concatSentence(CoreI18n.PLEASE_PRESS_F5_TO_REFRESH));
		}

		private void setIconAndTitle() {

			setPrecoloredIcon(language.getLangageFlag());
			setTitle(CoreI18n.CHANGE_LANGUAGE_TO_ARG1.toDisplay(language.getMixedLanguageDisplayString()));
		}
	}
}
