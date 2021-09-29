package com.softicar.platform.common.string.trim;

import org.junit.Assert;
import org.junit.Test;

public class MultiLineStringTrimmerTest extends Assert {

	@Test
	public void test() {

		assertTrimmedString(" abc ", " abc ");
		assertTrimmedString(" abc ", " \n abc \n ");
		assertTrimmedString(" abc ", " \r\n abc \r\n ");
		assertTrimmedString(" abc \n abc ", " \r\n abc \n abc \r\n ");
	}

	private void assertTrimmedString(String expectedString, String inputString) {

		String actualString = new MultiLineStringTrimmer(inputString).trim();

		assertEquals(expectedString, actualString);
	}
}
