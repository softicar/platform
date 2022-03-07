package com.softicar.platform.dom.elements.input;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.AbstractDomValueInputTest;
import org.junit.Test;

public class DomDoubleInputTest extends AbstractDomValueInputTest<Double> {

	public DomDoubleInputTest() {

		super(DomDoubleInput::new);
	}

	@Test
	public void testGetValue() {

		// test blank input
		assertEmptyResultForGetValue("");
		assertEmptyResultForGetValue(" ");

		// test valid inputs
		assertResultForGetValue(0.0, "0");
		assertResultForGetValue(123.0, "123");
		assertResultForGetValue(-123.45, "-123.45");
		assertResultForGetValue(3.12345678, "3.12345678");
		assertResultForGetValue(3.12345678, "3,12345678");

		// test illegal inputs
		assertExceptionForGetValue(DomI18n.INVALID_FLOATING_POINT_NUMBER, "foo");
		assertExceptionForGetValue(DomI18n.INVALID_FLOATING_POINT_NUMBER, "3.4.2");
		assertExceptionForGetValue(DomI18n.INVALID_FLOATING_POINT_NUMBER, "3;2");
		assertExceptionForGetValue(DomI18n.INVALID_FLOATING_POINT_NUMBER, "3 2");
	}
}
