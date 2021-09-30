package com.softicar.platform.common.container.pair;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ComparablePairTest {

	private final ComparablePair<Integer, Integer> pair1 = ComparablePairFactory.create(2, 3);
	private final ComparablePair<Integer, Integer> pair2 = ComparablePairFactory.create(3, 2);

	@Test
	public void comparesCorrectly() {

		assertTrue(pair1.compareTo(pair2) < 0);
		assertTrue(pair2.compareTo(pair1) > 0);
		assertEquals(0, pair1.compareTo(pair1));
		assertEquals(0, pair2.compareTo(pair2));
	}
}
