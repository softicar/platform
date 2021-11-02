package com.softicar.platform.common.core.thread.utils;

import org.junit.Assert;
import org.junit.Test;

public class ThreadSafeLazySupplierTest extends Assert {

	private static final String A = "A";

	@Test
	public void test() {

		assertNull(new ThreadSafeLazySupplier<>(() -> null).get());
		assertEquals(A, new ThreadSafeLazySupplier<>(() -> A).get());
	}

	@Test(expected = ThreadSafeLazySupplierException.class)
	public void testCycle() {

		new CycleExample().get();
	}

	private class CycleExample {

		private final ThreadSafeLazySupplier<Integer> supplier;

		public CycleExample() {

			this.supplier = new ThreadSafeLazySupplier<>(this::get);
		}

		public Integer get() {

			return supplier.get();
		}
	}
}
