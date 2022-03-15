package com.softicar.platform.common.core.number.formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.Assert;
import org.junit.Test;

public class BigDecimalScaleApplierTest extends Assert {

	private BigDecimalScaleApplier applier;

	@Test
	public void testWithoutScaleAndRoundingMode() {

		this.applier = new BigDecimalScaleApplier();

		assertResult("123", "123");
		assertResult("123.4", "123.4");
		assertResult("123.45", "123.45");
		assertResult("123.454", "123.454");
		assertResult("123.455", "123.455");
	}

	@Test
	public void testWithScaleAndWithoutRoundingMode() {

		this.applier = new BigDecimalScaleApplier()//
			.setScale(2);

		assertResult("123.00", "123");
		assertResult("123.40", "123.4");
		assertResult("123.45", "123.45");
		assertResult("123.45", "123.450");
		assertResult("123.454", "123.454");
		assertResult("123.455", "123.455");
	}

	@Test
	public void testWithScaleAndRoundingMode() {

		this.applier = new BigDecimalScaleApplier()//
			.setScale(2)
			.setRoundingMode(RoundingMode.HALF_UP);

		assertResult("123.00", "123");
		assertResult("123.40", "123.4");
		assertResult("123.45", "123.45");
		assertResult("123.45", "123.454");
		assertResult("123.46", "123.455");
	}

	@Test
	public void testWithNegativeScaleAndRoundingMode() {

		this.applier = new BigDecimalScaleApplier()//
			.setScale(-2)
			.setRoundingMode(RoundingMode.HALF_UP);

		assertResult("1.23E4", "12300");
		assertResult("1.23E4", "12349");
		assertResult("1.24E4", "12350");
	}

	private void assertResult(String expected, String input) {

		assertEquals(new BigDecimal(expected), applier.apply(new BigDecimal(input)));
	}
}
