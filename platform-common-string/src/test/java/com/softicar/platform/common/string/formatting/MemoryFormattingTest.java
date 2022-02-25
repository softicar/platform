package com.softicar.platform.common.string.formatting;

import static org.junit.Assert.assertEquals;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.junit.Test;

public class MemoryFormattingTest {

	private static final int VALUE_1234 = 1234;
	private final char decimalSeparator;

	public MemoryFormattingTest() {

		decimalSeparator = new DecimalFormatSymbols(Locale.getDefault()).getDecimalSeparator();
	}

	@Test
	public void formatsZero() {

		assertEquals("0 B", MemoryFormatting.formatMemory(0, 0));
		assertEquals("0" + decimalSeparator + "0 B", MemoryFormatting.formatMemory(0, 1));
		assertEquals("0" + decimalSeparator + "00 B", MemoryFormatting.formatMemory(0, 2));
	}

	@Test
	public void formatsDividesValues() {

		assertEquals("1 KiB", MemoryFormatting.formatMemory(VALUE_1234, 0));
		assertEquals("1" + decimalSeparator + "2 KiB", MemoryFormatting.formatMemory(VALUE_1234, 1));
		assertEquals("1" + decimalSeparator + "21 KiB", MemoryFormatting.formatMemory(VALUE_1234, 2));
	}

	@Test
	public void formatsNegativeValues() {

		assertEquals("-1 KiB", MemoryFormatting.formatMemory(-VALUE_1234, 0));
		assertEquals("-1" + decimalSeparator + "2 KiB", MemoryFormatting.formatMemory(-VALUE_1234, 1));
		assertEquals("-1" + decimalSeparator + "21 KiB", MemoryFormatting.formatMemory(-VALUE_1234, 2));
	}
}
