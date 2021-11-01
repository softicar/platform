package com.softicar.platform.common.core.supplier;

import org.junit.Assert;
import org.junit.Test;

public class LazySupplierTest extends Assert {

	@Test
	public void testGet() {

		int value = 33;
		LazySupplier<Integer> lazySupplier = new LazySupplier<>(() -> value);

		Integer a = lazySupplier.get();
		Integer b = lazySupplier.get();

		assertNotNull(a);
		assertEquals(value, a.intValue());
		assertSame(a, b);
	}
}
