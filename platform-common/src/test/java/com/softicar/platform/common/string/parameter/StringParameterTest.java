package com.softicar.platform.common.string.parameter;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * Test cases for {@link StringParameter}.
 *
 * @author Oliver Richers
 */
public class StringParameterTest extends AbstractTest {

	@Test
	public void testNormalCase() {

		StringParameter parameter = new StringParameter("foo", "bar");

		assertEquals("foo", parameter.getName());
		assertEquals("bar", parameter.getValue());
		assertEquals("bar", parameter.getValue("xxx"));
		assertFalse(parameter.isNull());
	}

	@Test
	public void testDefaultValue() {

		StringParameter parameter = new StringParameter("foo", null);

		assertEquals("xxx", parameter.getValue("xxx"));
		assertTrue(parameter.isNull());
	}

	@Test(expected = MissingParameterValueException.class)
	public void throwsMissingValueException() {

		StringParameter parameter = new StringParameter("foo", null);
		parameter.getValue();
	}
}
