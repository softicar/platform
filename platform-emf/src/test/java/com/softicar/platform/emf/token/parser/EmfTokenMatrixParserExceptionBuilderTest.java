package com.softicar.platform.emf.token.parser;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.Arrays;
import org.junit.Test;

public class EmfTokenMatrixParserExceptionBuilderTest extends AbstractTest {

	@Test
	public void test() {

		var exception = builder(0, "foo", "bar", "42").build();

		assertException(exception, "Row #1 could not be processed:\n" + "[foo], [bar], [42]");
	}

	@Test
	public void testWithReason() {

		var exception = builder(0, "foo", "bar", "42")//
			.setReason(IDisplayString.create("This is the reason."))
			.build();

		assertException(exception, "Row #1 could not be processed:\n" + "[foo], [bar], [42]\n" + "Reason: This is the reason.");
	}

	@Test
	public void testWithReasonAndDifferentRow() {

		var exception = builder(3, "foo", "bar", "42")//
			.setReason(IDisplayString.create("This is the reason."))
			.build();

		assertException(exception, "Row #4 could not be processed:\n" + "[foo], [bar], [42]\n" + "Reason: This is the reason.");
	}

	@Test
	public void testWithCurrentToken() {

		var exception = builder(0, "foo", "bar", "42")//
			.setCurrentToken("bar", 1)
			.build();

		assertException(exception, "Row #1, Column #2: Failed to process token [bar]:\n" + "[foo], **[bar]**, [42]");
	}

	@Test
	public void testWithCurrentTokenAndReason() {

		var exception = builder(0, "foo", "bar", "42")//
			.setCurrentToken("bar", 1)
			.setReason(IDisplayString.create("This is the reason."))
			.build();

		assertException(exception, "Row #1, Column #2: Failed to process token [bar]:\n" + "[foo], **[bar]**, [42]\n" + "Reason: This is the reason.");
	}

	@Test

	public void testWithCurrentTokenAndReasonAndDifferentRow() {

		var exception = builder(3, "foo", "bar", "42")//
			.setCurrentToken("bar", 1)
			.setReason(IDisplayString.create("This is the reason."))
			.build();

		assertException(exception, "Row #4, Column #2: Failed to process token [bar]:\n" + "[foo], **[bar]**, [42]\n" + "Reason: This is the reason.");
	}

	@Test
	public void testWithEmptyRow() {

		var exception = builder(0).build();

		assertException(exception, "Row #1 could not be processed:\n");
	}

	private EmfTokenMatrixParserExceptionBuilder builder(int rowIndex, String...row) {

		return new EmfTokenMatrixParserExceptionBuilder(rowIndex, Arrays.asList(row));
	}

	private void assertException(EmfTokenMatrixParserException exception, String expectedMessage) {

		assertEquals(expectedMessage, exception.getMessage());
	}
}
