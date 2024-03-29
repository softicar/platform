package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.i18n.key.AbstractI18nKey;
import com.softicar.platform.common.core.i18n.key.II18nKey;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.common.core.locale.LocaleScope;
import com.softicar.platform.common.testing.AbstractTest;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.Test;

public class I18nKeyDisplayStringTest extends AbstractTest {

	private static final LanguageEnum LANGUAGE = LanguageEnum.GERMAN;
	private static final LanguageEnum UNSUPPORTED_LANGUAGE = LanguageEnum.ESPERANTO;
	private final TestTranslator fallbackTranslator;

	public I18nKeyDisplayStringTest() {

		this.fallbackTranslator = new TestTranslator();

		CurrentLocale.set(new Locale().setLanguage(LANGUAGE));
		CurrentLanguageTranslator.set(fallbackTranslator);
	}

	@After
	public void cleanup() {

		CurrentLocale.set(null);
		CurrentLanguageTranslator.set(null);
	}

	@Test
	public void testWithUndefinedTranslation() {

		II18nKey key = new TestKey("foo");
		addFallback(key, "fallback");

		assertEquals("fallback", new I18nKeyDisplayString(key).toString());
	}

	@Test
	public void testWithDefinedTranslation() {

		II18nKey key = new TestKey("foo").de("bar");
		addFallback(key, "fallback");

		assertEquals("bar", new I18nKeyDisplayString(key).toString());
	}

	@Test
	public void testWithUnsupportedLanguage() {

		II18nKey key = new TestKey("foo").de("bar");
		addFallback(key, "fallback");

		CurrentLocale.set(new Locale().setLanguage(UNSUPPORTED_LANGUAGE));
		assertEquals("foo", new I18nKeyDisplayString(key).toString());
	}

	@Test
	public void testWithFormattingParameters() {

		II18nKey key = new TestKey("foo %s!").de("bar %s!");
		addFallback(key, "fallback %s!");

		assertEquals("bar 1337!", new I18nKeyDisplayString(key, 1337).toString());
	}

	@Test
	public void testWithDecimalParameters() {

		var englishLocale = new Locale()//
			.setLanguage(LanguageEnum.ENGLISH)
			.setDecimalSeparator(".")
			.setDigitGroupSeparator(",");
		var germanLocale = new Locale()//
			.setLanguage(LanguageEnum.GERMAN)
			.setDecimalSeparator(",")
			.setDigitGroupSeparator(".");

		II18nKey key = new TestKey("english %s!").de("german %s!");

		try (var scope = new LocaleScope(englishLocale)) {
			assertEquals("english 1,234,567.89!", new I18nKeyDisplayString(key, new BigDecimal("1234567.89")).toString());
			assertEquals("english 1,234,567.89!", new I18nKeyDisplayString(key, 1234567.89).toString());
			assertEquals("english 1,234,567.875!", new I18nKeyDisplayString(key, 1234567.89f).toString());
		}
		try (var scope = new LocaleScope(germanLocale)) {
			assertEquals("german 1.234.567,89!", new I18nKeyDisplayString(key, new BigDecimal("1234567.89")).toString());
			assertEquals("german 1.234.567,89!", new I18nKeyDisplayString(key, 1234567.89).toString());
			assertEquals("german 1.234.567,875!", new I18nKeyDisplayString(key, 1234567.89f).toString());
		}
	}

	@Test
	public void testWithDisplayableParameters() {

		II18nKey key = new TestKey("foo %s!");

		assertEquals("foo using-toDisplay()!", new I18nKeyDisplayString(key, new TestDisplayable()).toString());
	}

	// ------------------------------ private ------------------------------ //

	private void addFallback(II18nKey key, String fallback) {

		this.fallbackTranslator.put(LANGUAGE, key.toEnglish(), fallback);
	}

	private static class TestKey extends AbstractI18nKey<TestKey> {

		public TestKey(String english) {

			super(english);
		}

		@Override
		protected TestKey getThis() {

			return this;
		}
	}

	private static class TestDisplayable implements IDisplayable {

		@Override
		public IDisplayString toDisplay() {

			return IDisplayString.create("using-toDisplay()");
		}

		@Override
		public String toString() {

			return "using-toString()";
		}
	}
}
