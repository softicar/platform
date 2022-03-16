package com.softicar.platform.common.core.i18n.key.computer;

import org.junit.Assert;
import org.junit.Test;

public class I18nKeyComputerTest extends Assert {

	@Test
	public void testSimpleStringWithoutArguments() {

		assertEquals("THIS_IS_A_SIMPLE_STRING", new I18nKeyComputer("This is a simple string.").compute());
	}

	@Test
	public void testSimpleStringWithArguments() {

		assertEquals("THIS_IS_A_SIMPLE_ARG1_STRING", new I18nKeyComputer("This is a simple %s string.").compute());
	}

	@Test
	public void testSimpleStringWithDifferentArgumentTypes() {

		assertEquals("THIS_IS_A_ARG1_SIMPLE_ARG2_STRING", new I18nKeyComputer("This is a %2.3f simple %s string.").compute());
	}

	@Test
	public void testComplexStringWithoutArguments() {

		assertEquals("THIS_IS_A_COMPLEX_STRING", new I18nKeyComputer("  This#+ 'is'    a__complex #string.---____").compute());
	}

	@Test
	public void testComplexStringWithArguments() {

		assertEquals("THIS_IS_ARG1_A_COMPLEX_STRING_ARG2_ARG3", new I18nKeyComputer("  This#+ 'is' %s   a__complex #string.-- -%.3f-____%s").compute());
	}

	@Test
	public void testWithAdjacentDigits() {

		assertEquals("QWE_123_ASD", new I18nKeyComputer("Qwe 123 asd").compute());
	}

	@Test
	public void testWithLeadingDigit() {

		assertEquals("_0_IS_A_LEADING_DIGIT", new I18nKeyComputer("0 is a leading digit.").compute());
	}

	@Test
	public void testWithQuestionMark() {

		assertEquals("IS_THIS_A_SIMPLE_STRING_QUESTION", new I18nKeyComputer("Is this a simple string?").compute());
	}

	@Test
	public void testWithSeveralQuestionMarks() {

		assertEquals("IS_THIS_A_SIMPLE_STRING_QUESTION", new I18nKeyComputer("Is this a simple string???").compute());
	}

	@Test
	public void testWithEscapedPercentSign() {

		assertEquals("THIS_IS_A_100_PERCENT_SIMPLE_STRING", new I18nKeyComputer("This is a 100 %% simple string.").compute());
	}

	@Test
	public void testWithMultipleEscapedPercentSignRanges() {

		assertEquals("THIS_IS_100_PERCENT_OF_A_100_PERCENT_SIMPLE_STRING", new I18nKeyComputer("This is 100%%%% of a 100 %% simple string.").compute());
	}

	@Test
	public void testWithOnlySpecialCharacters() {

		assertEquals("", new I18nKeyComputer("*****").compute());
	}

	@Test
	public void testWithOnlySpecialCharacters2() {

		assertEquals("PERCENT_S", new I18nKeyComputer("%%%%%%%%s").compute());
	}

}
