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
	public void testCalculateNextBurstSizeWithDefaults() {

		assertEquals(1000, calculator.calculateNextBurstSize());
		assertEquals(2000, calculator.calculateNextBurstSize());
		assertEquals(4000, calculator.calculateNextBurstSize());

		testClock.add(Duration.ofSeconds(4));

		assertEquals(1000, calculator.calculateNextBurstSize());
		assertEquals(2000, calculator.calculateNextBurstSize());
		assertEquals(4000, calculator.calculateNextBurstSize());
		assertEquals(8000, calculator.calculateNextBurstSize());
		assertEquals(16000, calculator.calculateNextBurstSize());
	}

	@Test
	public void testCalculateNextBurstSizeWithDefaultsAndModifiedBurstSizeCooldown() {

		calculator.setBurstSizeCooldown(Duration.ofSeconds(10));

		assertEquals(1000, calculator.calculateNextBurstSize());
		assertEquals(2000, calculator.calculateNextBurstSize());
		assertEquals(4000, calculator.calculateNextBurstSize());

		testClock.add(Duration.ofSeconds(4));

		assertEquals(8000, calculator.calculateNextBurstSize());
		assertEquals(16000, calculator.calculateNextBurstSize());

		testClock.add(Duration.ofSeconds(11));

		assertEquals(1000, calculator.calculateNextBurstSize());
		assertEquals(2000, calculator.calculateNextBurstSize());
		assertEquals(4000, calculator.calculateNextBurstSize());
		assertEquals(8000, calculator.calculateNextBurstSize());
		assertEquals(16000, calculator.calculateNextBurstSize());
	}

	@Test
	public void testCalculateNextBurstSizeWithModifiedMinBurstSize() {

		calculator.setMinBurstSize(50);

		assertEquals(50, calculator.calculateNextBurstSize());
		assertEquals(100, calculator.calculateNextBurstSize());
		assertEquals(200, calculator.calculateNextBurstSize());
		assertEquals(400, calculator.calculateNextBurstSize());
		assertEquals(800, calculator.calculateNextBurstSize());
	}

	@Test
	public void testCalculateNextBurstSizeWithModifiedMaxBurstSize() {

		calculator.setMaxBurstSize(11111);

		assertEquals(1000, calculator.calculateNextBurstSize());
		assertEquals(2000, calculator.calculateNextBurstSize());
		assertEquals(4000, calculator.calculateNextBurstSize());
		assertEquals(8000, calculator.calculateNextBurstSize());
		assertEquals(11111, calculator.calculateNextBurstSize());
	}

	@Test
	public void testCalculateNextBurstSizeWithModifiedBurstSizeFactor() {

		calculator.setBurstSizeFactor(3);

		assertEquals(1000, calculator.calculateNextBurstSize());
		assertEquals(3000, calculator.calculateNextBurstSize());
		assertEquals(9000, calculator.calculateNextBurstSize());
		assertEquals(27000, calculator.calculateNextBurstSize());
		assertEquals(81000, calculator.calculateNextBurstSize());
	}
}
