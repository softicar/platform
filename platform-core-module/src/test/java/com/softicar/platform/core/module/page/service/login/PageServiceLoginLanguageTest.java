package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.testing.AbstractTest;
import jakarta.servlet.http.Cookie;
import java.util.Optional;
import org.junit.Test;

public class PageServiceLoginLanguageTest extends AbstractTest {

	@Test
	public void testGetChosenLanguage() {

		assertEquals(Optional.of(LanguageEnum.BOSNIAN), choose(new Cookie("other", "x"), new Cookie(PageServiceLoginLanguage.COOKIE, "bs")));
		assertEquals(Optional.of(LanguageEnum.GERMAN), choose(new Cookie(PageServiceLoginLanguage.COOKIE, "de")));
	}

	@Test
	public void testGetChosenLanguageIgnoresUnofferedAndUnknownLanguages() {

		assertEquals(Optional.empty(), choose());
		assertEquals(Optional.empty(), choose(new Cookie(PageServiceLoginLanguage.COOKIE, "fr")));
		assertEquals(Optional.empty(), choose(new Cookie(PageServiceLoginLanguage.COOKIE, "no-such-language")));
	}

	@Test
	public void testGetOwnName() {

		assertEquals("Bosanski", PageServiceLoginLanguage.getOwnName(LanguageEnum.BOSNIAN));
		assertEquals("English", PageServiceLoginLanguage.getOwnName(LanguageEnum.ENGLISH));
		assertEquals("Deutsch", PageServiceLoginLanguage.getOwnName(LanguageEnum.GERMAN));
	}

	private Optional<LanguageEnum> choose(Cookie...cookies) {

		return PageServiceLoginLanguage.getChosenLanguage(cookies);
	}
}
