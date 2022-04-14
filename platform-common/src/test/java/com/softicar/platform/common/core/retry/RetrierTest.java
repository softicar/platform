package com.softicar.platform.common.core.retry;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class RetrierTest extends AbstractTest {

	@Test
	public void testApplyWithoutFailure() {

		TestFunction testFunction = new TestFunction(0);

		new Retrier(testFunction)//
			.setTryCount(3)
			.setRetryDelayMillis(0)
			.apply();

		assertEquals(1, testFunction.getApplyCount());
	}

	@Test
	public void testApplyWithTwoOfThreeFailures() {

		TestFunction testFunction = new TestFunction(2);

		new Retrier(testFunction)//
			.setTryCount(3)
			.setRetryDelayMillis(0)
			.apply();

		assertEquals(3, testFunction.getApplyCount());
	}

	@Test(expected = TestExcetion.class)
	public void testApplyWithThreeOfThreeFailures() {

		new Retrier(new TestFunction(3))//
			.setTryCount(3)
			.setRetryDelayMillis(0)
			.apply();
	}

	@Test
	public void testApplyWithMatchingException() {

		TestFunction testFunction = new TestFunction(2);

		new Retrier(testFunction)//
			.setTryCount(3)
			.setRetryDecider(exception -> exception instanceof TestExcetion)
			.apply();

		assertEquals(3, testFunction.getApplyCount());
	}

	@Test(expected = TestExcetion.class)
	public void testApplyWithoutMatchingException() {

		TestFunction testFunction = new TestFunction(2);

		new Retrier(testFunction)//
			.setTryCount(3)
			.setRetryDecider(exception -> exception instanceof InterruptedException)
			.apply();
	}

	private static class TestFunction implements INullaryVoidFunction {

		private final int exceptionCount;
		private int applyCount;

		public TestFunction(int exceptionCount) {

			this.exceptionCount = exceptionCount;
			this.applyCount = 0;
		}

		public int getApplyCount() {

			return applyCount;
		}

		@Override
		public void apply() {

			++applyCount;

			if (applyCount <= exceptionCount) {
				throw new TestExcetion();
			}
		}
	}

	protected static class TestExcetion extends RuntimeException {

		private static final long serialVersionUID = 1L;
	}
}
