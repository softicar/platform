package com.softicar.platform.common.core.interfaces;

import org.junit.Assert;
import org.junit.Test;

public class IRefreshableTest extends Assert {

	@Test
	public void testOfNullableWithNull() {

		IRefreshable refreshable = IRefreshable.ofNullable(null);

		assertSame(IRefreshable.NO_OPERATION, refreshable);
	}

	@Test
	public void testOfNullableWithNonNull() {

		TestRefreshable testRefreshable = new TestRefreshable();

		IRefreshable refreshable = IRefreshable.ofNullable(testRefreshable);

		assertSame(testRefreshable, refreshable);
	}

	private static class TestRefreshable implements IRefreshable {

		@Override
		public void refresh() {

			// nothing to do
		}
	}
}
