package com.softicar.platform.dom.elements.input;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.AbstractDomValueInputTest;
import org.junit.Test;

public class DomLongInputTest extends AbstractDomValueInputTest<Long> {

	public DomLongInputTest() {

		super(DomLongInput::new);
	}

	@Test
	public void testGetValue() {

		// test blank input
		assertEmptyResultForGetValue("");
		assertEmptyResultForGetValue(" ");

		// test valid inputs
		assertResultForGetValue(0L, "0");
		assertResultForGetValue(123L, "123");
		assertResultForGetValue(-123L, "-123");
		assertResultForGetValue(123456789123456789L, "123456789123456789");

		// test illegal inputs
		assertExceptionForGetValue(DomI18n.INVALID_INTEGER, "foo");
		assertExceptionForGetValue(DomI18n.INVALID_INTEGER, "3.4");
		assertExceptionForGetValue(DomI18n.INVALID_INTEGER, "3 2");
	}
}
