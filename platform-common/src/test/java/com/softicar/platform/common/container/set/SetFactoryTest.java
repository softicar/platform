package com.softicar.platform.common.container.set;

import com.softicar.platform.common.container.map.MapFactory;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.Test;

public class SetFactoryTest extends AbstractTest {

	private static final Comparator<Integer> COMPARATOR = (a, b) -> a - b;

	@Test
	public void createsEmptyHashSet() {

		assertEquals(0, SetFactory.createHashSet().size());
	}

	@Test
	public void createsHashSetFromValues() {

		HashSet<Integer> set = SetFactory.createHashSetFrom(1, 2, 4);

		assertEquals(3, set.size());
		assertTrue(set.contains(1));
		assertTrue(set.contains(2));
		assertTrue(set.contains(4));
	}

	@Test
	public void createsEmptyTreeSet() {

		assertEquals(0, SetFactory.createTreeSet().size());
	}

	@Test
	public void createsTreeSetWithCorrectComparator() {

		assertSame(COMPARATOR, SetFactory.createTreeSet(COMPARATOR).comparator());
	}

	@Test
	public void copiesComparatorFromSortedSet() {

		SortedSet<Integer> sortedSet = SetFactory.createTreeSet(COMPARATOR);

		assertSame(COMPARATOR, SetFactory.createTreeSet(sortedSet).comparator());
	}

	@Test
	public void copiesComparatorFromImplicitSortedSet() {

		Iterable<Integer> iterable = SetFactory.createTreeSet(COMPARATOR);

		assertSame(COMPARATOR, SetFactory.createTreeSet(iterable).comparator());
	}

	@Test
	public void createsTreeSetFromValues() {

		TreeSet<Integer> set = SetFactory.createTreeSetFrom(1, 2, 4);

		assertEquals(3, set.size());
		assertTrue(set.contains(1));
		assertTrue(set.contains(2));
		assertTrue(set.contains(4));
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
