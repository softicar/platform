package com.softicar.platform.core.module.locale;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.locale.ILocale;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.emf.object.IEmfObject;

public class AGLocalizationPreset extends AGLocalizationPresetGenerated implements IEmfObject<AGLocalizationPreset> {

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
			.setDigitGroupSeparator(getDigitGroupSeparator());
	}

	private LanguageEnum getLanguageEnum() {

		return getLanguage().getLanguageEnum().orElse(LanguageEnum.ENGLISH);
	}
}
