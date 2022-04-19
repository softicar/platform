package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class LanguageEnumTest extends AbstractTest {

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
