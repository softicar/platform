package com.softicar.platform.common.core.locale;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class LocaleScopeTest extends AbstractTest {

	@Test
	public void test() {

		var originalLocale = CurrentLocale.get();

		var newLocale = new Locale();
		try (var scope = new LocaleScope(newLocale)) {
			assertSame(newLocale, CurrentLocale.get());
		}

		assertSame(originalLocale, CurrentLocale.get());
	}
}
