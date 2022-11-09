package com.softicar.platform.db.runtime.cache;

import com.softicar.platform.common.testing.AbstractTest;
import java.time.Duration;
import org.junit.Test;

public class DbTableRowCacheBurstSizeCalculatorTest extends AbstractTest {

	private final DbTableRowCacheBurstSizeCalculator calculator;

	public DbTableRowCacheBurstSizeCalculatorTest() {

		this.calculator = new DbTableRowCacheBurstSizeCalculator();
	}

	@Test
	public void testGetNextBurstSizeWithDefaults() {

		assertEquals(1000, calculator.getNextBurstSize());
		assertEquals(2000, calculator.getNextBurstSize());
		assertEquals(4000, calculator.getNextBurstSize());

		testClock.add(Duration.ofSeconds(4));

		assertEquals(1000, calculator.getNextBurstSize());
		assertEquals(2000, calculator.getNextBurstSize());
		assertEquals(4000, calculator.getNextBurstSize());
		assertEquals(8000, calculator.getNextBurstSize());
		assertEquals(16000, calculator.getNextBurstSize());
	}

	@Test
	public void testGetNextBurstSizeWithDefaultsAndModifiedBurstSizeCooldown() {

		calculator.setBurstSizeCooldown(Duration.ofSeconds(10));

		assertEquals(1000, calculator.getNextBurstSize());
		assertEquals(2000, calculator.getNextBurstSize());
		assertEquals(4000, calculator.getNextBurstSize());

		testClock.add(Duration.ofSeconds(4));

		assertEquals(8000, calculator.getNextBurstSize());
		assertEquals(16000, calculator.getNextBurstSize());

		testClock.add(Duration.ofSeconds(11));

		assertEquals(1000, calculator.getNextBurstSize());
		assertEquals(2000, calculator.getNextBurstSize());
		assertEquals(4000, calculator.getNextBurstSize());
		assertEquals(8000, calculator.getNextBurstSize());
		assertEquals(16000, calculator.getNextBurstSize());
	}

	@Test
	public void testGetNextBurstSizeWithModifiedMinBurstSize() {

		calculator.setMinBurstSize(50);

		assertEquals(50, calculator.getNextBurstSize());
		assertEquals(100, calculator.getNextBurstSize());
		assertEquals(200, calculator.getNextBurstSize());
		assertEquals(400, calculator.getNextBurstSize());
		assertEquals(800, calculator.getNextBurstSize());
	}

	@Test
	public void testGetNextBurstSizeWithModifiedMaxBurstSize() {

		calculator.setMaxBurstSize(11111);

		assertEquals(1000, calculator.getNextBurstSize());
		assertEquals(2000, calculator.getNextBurstSize());
		assertEquals(4000, calculator.getNextBurstSize());
		assertEquals(8000, calculator.getNextBurstSize());
		assertEquals(11111, calculator.getNextBurstSize());
	}

	@Test
	public void testGetNextBurstSizeWithModifiedBurstSizeFactor() {

		calculator.setBurstSizeFactor(3);

		assertEquals(1000, calculator.getNextBurstSize());
		assertEquals(3000, calculator.getNextBurstSize());
		assertEquals(9000, calculator.getNextBurstSize());
		assertEquals(27000, calculator.getNextBurstSize());
		assertEquals(81000, calculator.getNextBurstSize());
	}
}
