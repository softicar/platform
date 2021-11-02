package com.softicar.platform.common.core.i18n;

import org.junit.Assert;
import org.junit.Test;

public class LanguageEnumTest extends Assert {

	@Test
	public void testByIso6391() {

		for (LanguageEnum languageEnum: LanguageEnum.values()) {
			assertSame(languageEnum, LanguageEnum.getByIso6391(languageEnum.getIso6391()));
		}
	}

	@Test
	public void testByName() {

		for (LanguageEnum languageEnum: LanguageEnum.values()) {
			assertSame(languageEnum, LanguageEnum.getByName(languageEnum.name()));
		}
	}
}
