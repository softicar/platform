package com.softicar.platform.common.string.parameter;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * Test cases for {@link IntegerParameter}.
 *
 * @author Oliver Richers
 */
public class IntegerParameterTest extends AbstractTest {

	private static final int SOME_NUMBER = 1337;
	private static final int SOME_OTHER_NUMBER = 13;

	@Test
	public void testNormalCase() {

		IntegerParameter parameter = new IntegerParameter("foo", "" + SOME_NUMBER);

		assertEquals("foo", parameter.getName());
		assertEquals(SOME_NUMBER, parameter.getValue().intValue());
		assertEquals(SOME_NUMBER, parameter.getValue(SOME_OTHER_NUMBER).intValue());
		assertFalse(parameter.isNull());
	}

	@Test
	public void testMissingValue() {

		IntegerParameter parameter = new IntegerParameter("foo", null);

		assertEquals(SOME_OTHER_NUMBER, parameter.getValue(SOME_OTHER_NUMBER).intValue());
		assertTrue(parameter.isNull());
	}

	@Test(expected = MissingParameterValueException.class)
	public void throwsMissingValueException() {

		IntegerParameter parameter = new IntegerParameter("foo", null);
		parameter.getValue();
	}

	@Test(expected = InvalidParameterValueException.class)
	public void throwsInvalidValueException() {

		IntegerParameter parameter = new IntegerParameter("foo", "e13");
		parameter.getValue();
	}

	@Test(expected = InvalidParameterValueException.class)
	public void throwsInvalidValueExceptionInsteadOfReturningDefaultValue() {

		IntegerParameter parameter = new IntegerParameter("foo", "e13");
		parameter.getValue(13);
	}
}
