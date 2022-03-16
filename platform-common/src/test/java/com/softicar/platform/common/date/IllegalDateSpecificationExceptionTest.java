package com.softicar.platform.common.date;

import org.junit.Assert;
import org.junit.Test;

public class IllegalDateSpecificationExceptionTest extends Assert {

	@Test
	public void testFormatting() {

		assertEquals(//
			CommonDateI18n.ILLEGAL_DATE_SPECIFICATION_ARG1.toDisplay("2022-02-03").toString(),
			new IllegalDateSpecificationException(2022, 2, 3).getMessage());
	}
}
