package com.softicar.platform.emf.data.table.util;

import com.softicar.platform.common.testing.Asserts;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ListShifterTest extends Asserts {

	@Test
	public void testShiftList() {

		var list = new ArrayList<>(List.of("A", "B", "C", "D"));
		ListShifter.shiftList(list, 1, 2);
		assertEquals("A", list.get(0));
		assertEquals("C", list.get(1));
		assertEquals("B", list.get(2));
		assertEquals("D", list.get(3));
	}

	@Test
	public void testShiftListWithIdenticalIndexes() {

		var list = new ArrayList<>(List.of("A", "B", "C", "D"));
		ListShifter.shiftList(list, 1, 1);
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		assertEquals("C", list.get(2));
		assertEquals("D", list.get(3));
	}

	@Test(expected = NullPointerException.class)
	public void testShiftListWithListNull() {

		ListShifter.shiftList(null, 1, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShiftListWithListEmpty() {

		var list = new ArrayList<>();
		ListShifter.shiftList(list, 1, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShiftListWithSourceIndexTooHigh() {

		var list = new ArrayList<>(List.of("A", "B", "C", "D"));
		ListShifter.shiftList(list, 999, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShiftListWithSourceIndexTooLow() {

		var list = new ArrayList<>(List.of("A", "B", "C", "D"));
		ListShifter.shiftList(list, -1, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShiftListWithTargetIndexTooHigh() {

		var list = new ArrayList<>(List.of("A", "B", "C", "D"));
		ListShifter.shiftList(list, 1, 999);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShiftListWithTargetIndexTooLow() {

		var list = new ArrayList<>(List.of("A", "B", "C", "D"));
		ListShifter.shiftList(list, 1, -1);
	}
}
