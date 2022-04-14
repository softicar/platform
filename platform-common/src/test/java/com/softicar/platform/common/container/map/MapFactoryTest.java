package com.softicar.platform.common.container.map;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import org.junit.Test;

public class MapFactoryTest extends AbstractTest {

	private static final Comparator<Integer> COMPARATOR = (a, b) -> a - b;

	@Test
	public void createsEmptyHashMap() {

		assertEquals(0, MapFactory.createHashMap().size());
	}

	@Test
	public void createsEmptyIdentityHashMap() {

		assertEquals(0, MapFactory.createIdentityHashMap().size());
	}

	@Test
	public void createsEmptyTreeMap() {

		assertEquals(0, MapFactory.createTreeMap().size());
	}

	@Test
	public void createsTreeMapWithCorrectComparator() {

		assertSame(COMPARATOR, MapFactory.createTreeMap(COMPARATOR).comparator());
	}

	@Test
	public void copiesComparatorFromSortedMap() {

		SortedMap<Integer, String> sortedMap = MapFactory.createTreeMap(COMPARATOR);

		assertSame(COMPARATOR, MapFactory.createTreeMap(sortedMap).comparator());
	}

	@Test
	public void copiesComparatorFromImplicitSortedMap() {

		Map<Integer, String> map = MapFactory.createTreeMap(COMPARATOR);

		assertSame(COMPARATOR, MapFactory.createTreeMap(map).comparator());
	}

	@Test
	public void copiesEntriesFromSourceMap() {

		Map<Integer, String> sourceMap = MapFactory.createTreeMap();
		sourceMap.put(1, "foo");
		sourceMap.put(2, "bar");
		Map<Integer, String> map = MapFactory.createTreeMap(sourceMap);

		assertNotSame(sourceMap, map);
		assertEquals(2, map.size());
		assertEquals("foo", map.get(1));
		assertEquals("bar", map.get(2));
	}
}
