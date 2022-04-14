package com.softicar.platform.common.core.item;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;
import org.mockito.Mockito;

public class BasicItemComparatorTest extends AbstractTest {

	private static final ItemId _23 = new ItemId(23);
	private static final ItemId _34 = new ItemId(34);
	private static final ItemId _44 = new ItemId(44);
	private final IBasicItem a;
	private final IBasicItem b;

	public BasicItemComparatorTest() {

		this.a = Mockito.mock(IBasicItem.class);
		this.b = Mockito.mock(IBasicItem.class);
	}

	@Test
	public void testCompareWithLess() {

		Mockito.when(a.getItemId()).thenReturn(_34);
		Mockito.when(b.getItemId()).thenReturn(_44);

		assertEquals(-1, BasicItemComparator.get().compare(a, b));
	}

	@Test
	public void testCompareWithEqual() {

		Mockito.when(a.getItemId()).thenReturn(_34);
		Mockito.when(b.getItemId()).thenReturn(_34);

		assertEquals(0, BasicItemComparator.get().compare(a, b));
	}

	@Test
	public void testCompareWithGreater() {

		Mockito.when(a.getItemId()).thenReturn(_34);
		Mockito.when(b.getItemId()).thenReturn(_23);

		assertEquals(1, BasicItemComparator.get().compare(a, b));
	}
}
