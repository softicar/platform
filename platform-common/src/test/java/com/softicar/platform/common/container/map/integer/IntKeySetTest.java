package com.softicar.platform.common.container.map.integer;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;

public class IntKeySetTest extends AbstractTest {

	private static final int TEST_COUNT = 2 * AbstractIntKeySet.MIN_CAPACITY;
	private final TestMap map;
	private final Map<Integer, TestValue> values;

	public IntKeySetTest() {

		this.map = new TestMap();
		this.values = new TreeMap<>();
	}

	@Test
	public void testEmptyMap() {

		assertTrue(map.isEmpty());
		assertFalse(map.iterator().hasNext());
		assertEquals(0, map.size());
	}

	@Test
	public void testRemovingNonExistingElement() {

		TestValue value = map.remove(12345);
		assertNull(value);
	}

	@Test
	public void testAddingAndRemovingElements() {

		// add elements
		for (int i = 0; i < TEST_COUNT; ++i) {
			map.add(new TestValue(i));

			assertFalse(map.isEmpty());
			assertTrue(map.iterator().hasNext());
			assertEquals(i + 1, map.size());
		}
	}

	@Test
	public void testRemovingElements() {

		// fill map
		fillMap(TEST_COUNT);

		// remove elements
		for (int i = 0; i < TEST_COUNT; ++i) {
			assertFalse(map.isEmpty());
			assertTrue(map.iterator().hasNext());
			assertEquals(TEST_COUNT - i, map.size());

			TestValue value = map.remove(i);
			assertSame(value, values.get(i));
		}

		// final check
		assertTrue(map.isEmpty());
		assertFalse(map.iterator().hasNext());
		assertEquals(0, map.size());
	}

	@Test
	public void testGettingElements() {

		// fill map
		fillMap(TEST_COUNT);

		// try to get elements
		for (int i = 0; i < TEST_COUNT; ++i) {
			TestValue value = map.get(i);
			assertSame(values.get(i), value);
		}
	}

	@Test
	public void testGrowingAndShrinking() {

		// insert enough elements to fill capacity
		int initialCapacity = map.getCapacity();
		for (int i = 0; i < initialCapacity; ++i) {
			map.add(new TestValue(i));
		}

		// capacity must be higher now
		int grownCapacity = map.getCapacity();
		assertTrue(grownCapacity > initialCapacity);

		// remove all elements
		for (int i = 0; i < initialCapacity; ++i) {
			map.remove(i);
		}

		// map must be empty now and capacity back to initial value
		assertTrue(map.isEmpty());
		assertEquals(0, map.size());
		assertEquals(initialCapacity, map.getCapacity());
	}

	private void fillMap(final int count) {

		for (int i = 0; i < count; ++i) {
			TestValue value = new TestValue(i);
			map.add(value);
			values.put(i, value);
		}
	}
}
