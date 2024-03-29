package com.softicar.platform.common.string.charset;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * Test cases for {@link Charsets}.
 *
 * @author Oliver Richers
 */
public class CharsetsTest extends AbstractTest {

	@Test
	public void allCharsetsAreValid() {

		assertNotNull(Charsets.ISO_8859_1);
		assertNotNull(Charsets.ISO_8859_2);
		assertNotNull(Charsets.ISO_8859_15);

		assertNotNull(Charsets.UTF8);
		assertNotNull(Charsets.UTF16);
		assertNotNull(Charsets.UTF32);

		assertNotNull(Charsets.WINDOWS_1250);
		assertNotNull(Charsets.WINDOWS_1252);
	}
}
