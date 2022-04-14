package com.softicar.platform.common.date;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class IllegalDateSpecificationExceptionTest extends AbstractTest {

	@Test
	public void testFormatting() {

		assertEquals(//
			CommonDateI18n.ILLEGAL_DATE_SPECIFICATION_ARG1.toDisplay("2022-02-03").toString(),
			new IllegalDateSpecificationException(2022, 2, 3).getMessage());
	}
}
