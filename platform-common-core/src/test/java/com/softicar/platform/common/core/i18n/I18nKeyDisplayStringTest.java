package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.i18n.key.AbstractI18nKey;
import com.softicar.platform.common.core.i18n.key.II18nKey;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.locale.Locale;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class I18nKeyDisplayStringTest extends Assert {

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
}
