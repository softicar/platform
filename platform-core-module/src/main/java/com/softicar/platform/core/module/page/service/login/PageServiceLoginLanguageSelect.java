package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.DomOption;
import com.softicar.platform.dom.input.DomSelect;

/**
 * Chooses the language of the login page, see
 * {@link PageServiceLoginLanguage}; every language is shown in its own name.
 */
public class PageServiceLoginLanguageSelect extends DomSelect<PageServiceLoginLanguageSelect.LanguageOption> implements IDomChangeEventHandler {

	public PageServiceLoginLanguageSelect() {

		setAttribute("aria-label", CoreI18n.LANGUAGE.toString());
		setTitle(CoreI18n.LANGUAGE);
		addMarker(CoreTestMarker.PAGE_SERVICE_LOGIN_LANGUAGE_SELECT);
		LanguageEnum current = CurrentLocale.get().getLanguage();
		for (LanguageEnum language: PageServiceLoginLanguage.LANGUAGES) {
			LanguageOption option = appendChild(new LanguageOption(language));
			if (language == current) {
				setSelectedOption(option);
			}
		}
	}

	@Override
	public void handleChange(IDomEvent event) {

		LanguageOption option = getSelectedOption();
		if (option != null) {
			getDomEngine().executeScriptCode(PageServiceLoginLanguage.getChooseScriptCode(option.getLanguage()));
		}
	}

	/**
	 * The option of one language.
	 */
	public static class LanguageOption extends DomOption {

		private final LanguageEnum language;

		public LanguageOption(LanguageEnum language) {

			this.language = language;
			setAttribute("lang", language.getIso6391());
			appendText(PageServiceLoginLanguage.getOwnName(language));
		}

		public LanguageEnum getLanguage() {

			return language;
		}
	}
}
