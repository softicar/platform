package com.softicar.platform.common.container.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.softicar.platform.common.container.comparator.ReverseComparator;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.junit.Test;

public class SortedMapsTest {

	private final Comparator<Integer> comparator;
	private final SortedMap<Integer, String> sortedMap;
	private final SortedMap<Integer, String> reverseMap;

	public SortedMapsTest() {

		this.comparator = new ReverseComparator<>(Integer::compare);
		this.sortedMap = MapFactory.createTreeMap();
		this.sortedMap.put(1, "1");
		this.sortedMap.put(3, "3");
		this.sortedMap.put(4, "4");
		this.sortedMap.put(7, "7");
		this.sortedMap.put(9, "9");
		this.reverseMap = new TreeMap<>(comparator);
		this.reverseMap.putAll(sortedMap);
	}

	@Test
	public void createsTreeMapWithSpecifiedComparator() {

		TreeMap<Integer, Integer> map = MapFactory.createTreeMap(comparator);

		assertSame(comparator, map.comparator());
	}

	// -------------------------------- preceding -------------------------------- //

	@Test
	public void returnsCorrectPrecedingEntry() {

		Map.Entry<Integer, String> preceding = SortedMaps.getPrecedingEntry(sortedMap, 3);
		assertEquals(1, preceding.getKey().intValue());
		assertEquals("1", preceding.getValue());
	}

	@Test
	public void returnsCorrectPrecedingValueWithNonContainedKey() {

		Map.Entry<Integer, String> preceding = SortedMaps.getPrecedingEntry(sortedMap, 12);
		assertEquals(9, preceding.getKey().intValue());
		assertEquals("9", preceding.getValue());
	}

	@Test
	public void returnsCorrectPrecedingEntryWithComparator() {

		Map.Entry<Integer, String> preceding = SortedMaps.getPrecedingEntry(reverseMap, 3);
		assertEquals(4, preceding.getKey().intValue());
		assertEquals("4", preceding.getValue());
	}

	@Test
	public void returnsNullWhenNoPrecedingEntry() {

		assertNull(SortedMaps.getPrecedingEntry(sortedMap, 1));
	}

	// -------------------------------- succeeding -------------------------------- //

	@Test
	public void returnsCorrectSucceedingEntry() {

		Map.Entry<Integer, String> succeeding = SortedMaps.getSucceedingEntry(sortedMap, 3);
		assertEquals(4, succeeding.getKey().intValue());
		assertEquals("4", succeeding.getValue());
	}

	@Test
	public void returnsCorrectSucceedingValueWithNonContainedKey() {

		Map.Entry<Integer, String> succeeding = SortedMaps.getSucceedingEntry(sortedMap, 5);
		assertEquals(7, succeeding.getKey().intValue());
		assertEquals("7", succeeding.getValue());
	}

	@Test
	public void returnsCorrectSucceedingEntryWithComparator() {

		Map.Entry<Integer, String> succeeding = SortedMaps.getSucceedingEntry(reverseMap, 3);
		assertEquals(1, succeeding.getKey().intValue());
		assertEquals("1", succeeding.getValue());
	}

	@Test
	public void returnsNullWhenNoSucceedingEntry() {

		assertNull(SortedMaps.getSucceedingEntry(sortedMap, 9));
	}

	// -------------------------------- entry -------------------------------- //

	@Test
	public void returnsCorrectEntry() {

		assertEquals("4", SortedMaps.getEntry(sortedMap, 4).getValue());
	}

	@Test
	public void returnsCorrectEntryWithComparator() {

		assertEquals("7", SortedMaps.getEntry(reverseMap, 7).getValue());
	}

	@Test
	public void returnsNullEntryOnMissingEntry() {

		assertNull(SortedMaps.getEntry(sortedMap, 5));
	}

	// -------------------------------- first and last entry -------------------------------- //

	@Test
	public void returnsCorrectFirstEntry() {

		assertEquals("1", SortedMaps.getFirstEntry(sortedMap).getValue());
	}

	@Test
	public void returnsCorrectLastEntry() {

		assertEquals("9", SortedMaps.getLastEntry(sortedMap).getValue());
	}

	@Test
	public void returnsCorrectLastEntryWithComparator() {

		assertEquals("1", SortedMaps.getLastEntry(reverseMap).getValue());
	}
}
