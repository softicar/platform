package com.softicar.platform.common.string.parsing;

import org.junit.Assert;
import org.junit.Test;

public class NumberStringCleanerTest {

	@Test
	public void testDoubleStringCleanerApplied() {

		//
		// formatting applied
		//
		Assert.assertEquals("1234567.890", NumberStringCleaner.convertToCleanNumberString("1,234,567.890"));
		Assert.assertEquals("1234567.890", NumberStringCleaner.convertToCleanNumberString("1.234.567,890"));

		Assert.assertEquals("1234567.890", NumberStringCleaner.convertToCleanNumberString("1 234 567,890"));

		Assert.assertEquals("-1234567.890", NumberStringCleaner.convertToCleanNumberString("-1.234.567,890"));

		Assert.assertEquals(".890", NumberStringCleaner.convertToCleanNumberString(",890"));

		Assert.assertEquals("2.1", NumberStringCleaner.convertToCleanNumberString("2.1"));

		//
		// formatting not applied
		//
		String input1 = "Qwe a.s.d.";
		Assert.assertEquals(input1, NumberStringCleaner.convertToCleanNumberString(input1));

		String input2 = "Qwe a.s.d. ";
		Assert.assertEquals(input2, NumberStringCleaner.convertToCleanNumberString(input2));

		String input3 = "Qwe a.s.1.";
		Assert.assertEquals(input3, NumberStringCleaner.convertToCleanNumberString(input3));

		String input4 = "Qwe a.s.1";
		Assert.assertEquals(input4, NumberStringCleaner.convertToCleanNumberString(input4));

		String input5 = "1,234,567.a90";
		Assert.assertEquals(input5, NumberStringCleaner.convertToCleanNumberString(input5));

		String input6 = "a.2";
		Assert.assertEquals(input6, NumberStringCleaner.convertToCleanNumberString(input6));

		String input7 = "2.a";
		Assert.assertEquals(input7, NumberStringCleaner.convertToCleanNumberString(input7));

		String input8 = "2014-09-26 13:04:20.15";
		Assert.assertEquals(input8, NumberStringCleaner.convertToCleanNumberString(input8));

		String input9 = "11.12.2013";
		Assert.assertEquals(input9, NumberStringCleaner.convertToCleanNumberString(input9));
	}
}
