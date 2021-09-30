package com.softicar.platform.common.container.map.map;

import org.junit.Assert;
import org.junit.Test;

public class MapMapTest extends Assert {

	private final IMapMap<Integer, Integer, Integer> mapMap;

	public MapMapTest() {

		this.mapMap = MapMapFactory.create();
	}

	@Test
	public void testEmptyMap() {

		assertFalse(mapMap.contains(1, 1));
		assertFalse(mapMap.containsKey(1));
		assertNull(mapMap.get(1, 2));
		assertTrue(mapMap.isEmpty());
		assertEquals(0, mapMap.keySet().size());
		assertEquals(0, mapMap.size());
	}

	@Test
	public void testPuttingOneValue() {

		mapMap.put(1, 2, 3);

		assertTrue(mapMap.contains(1, 2));
		assertTrue(mapMap.containsKey(1));
		assertEquals(3, mapMap.get(1, 2).intValue());
		assertFalse(mapMap.isEmpty());
		assertEquals(1, mapMap.keySet().size());
		assertEquals(1, mapMap.size());
	}

	@Test
	public void testPuttingTwoValuesWithEqualPrimaryKey() {

		mapMap.put(1, 1, 1);
		mapMap.put(1, 2, 3);

		assertTrue(mapMap.contains(1, 1));
		assertTrue(mapMap.contains(1, 2));
		assertTrue(mapMap.containsKey(1));
		assertEquals(1, mapMap.get(1, 1).intValue());
		assertEquals(3, mapMap.get(1, 2).intValue());
		assertFalse(mapMap.isEmpty());
		assertEquals(1, mapMap.keySet().size());
		assertEquals(1, mapMap.size());
	}

	@Test
	public void testPuttingTwoValuesWithDifferentPrimaryKeys() {

		mapMap.put(1, 2, 3);
		mapMap.put(2, 3, 4);

		assertTrue(mapMap.contains(1, 2));
		assertTrue(mapMap.contains(2, 3));
		assertTrue(mapMap.containsKey(1));
		assertTrue(mapMap.containsKey(2));
		assertEquals(3, mapMap.get(1, 2).intValue());
		assertEquals(4, mapMap.get(2, 3).intValue());
		assertFalse(mapMap.isEmpty());
		assertEquals(2, mapMap.keySet().size());
		assertEquals(2, mapMap.size());
	}

	@Test
	public void testRemovingValues() {

		mapMap.put(1, 2, 3);
		mapMap.put(2, 3, 4);
		Integer value = mapMap.remove(1, 2);

		assertEquals(3, value.intValue());
		assertFalse(mapMap.contains(1, 2));
		assertTrue(mapMap.contains(2, 3));
		assertFalse(mapMap.containsKey(1));
		assertTrue(mapMap.containsKey(2));
		assertNull(mapMap.get(1, 2));
		assertEquals(4, mapMap.get(2, 3).intValue());
		assertFalse(mapMap.isEmpty());
		assertEquals(1, mapMap.keySet().size());
		assertEquals(1, mapMap.size());
	}

	@Test
	public void testClearingMap() {

		mapMap.put(1, 2, 3);
		mapMap.put(2, 3, 4);
		mapMap.clear();

		assertFalse(mapMap.contains(1, 2));
		assertFalse(mapMap.contains(2, 3));
		assertFalse(mapMap.containsKey(1));
		assertNull(mapMap.get(1, 2));
		assertTrue(mapMap.isEmpty());
		assertEquals(0, mapMap.keySet().size());
		assertEquals(0, mapMap.size());
	}
}
