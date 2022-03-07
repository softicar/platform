package com.softicar.platform.dom.elements.input;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.AbstractDomValueInputTest;
import org.junit.Test;

public class DomIntegerInputTest extends AbstractDomValueInputTest<Integer> {

	public DomIntegerInputTest() {

		super(DomIntegerInput::new);
	}

	@Test
	public void testGetValue() {

		// test blank input
		assertEmptyResultForGetValue("");
		assertEmptyResultForGetValue(" ");

		// test valid inputs
		assertResultForGetValue(0, "0");
		assertResultForGetValue(123, "123");
		assertResultForGetValue(-123, "-123");

		// test illegal inputs
		assertExceptionForGetValue(DomI18n.INVALID_INTEGER, "foo");
		assertExceptionForGetValue(DomI18n.INVALID_INTEGER, "3.4");
		assertExceptionForGetValue(DomI18n.INVALID_INTEGER, "3 2");
	}
}
