package com.softicar.platform.core.module.localization;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.locale.ILocale;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.emf.object.IEmfObject;

public class AGLocalization extends AGLocalizationGenerated implements IEmfObject<AGLocalization> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}

	public ILocale getLocale() {

		return new Locale()//
			.setLanguage(getLanguageEnum())
			.setDecimalSeparator(getDecimalSeparator())
			.setDigitGroupSeparator(getDigitGroupSeparator())
			.setDateFormat(getDateFormat())
			.setLocalizedDateFormat(getLocalizedDateFormat())
			.setLocalizedTimeFormat(getLocalizedTimeFormat());
	}

	private LanguageEnum getLanguageEnum() {

		return getLanguage().getLanguageEnum().orElse(LanguageEnum.ENGLISH);
	}
}
