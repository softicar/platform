package com.softicar.platform.common.container.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ListElementsMoverTest extends Assert {

	private final List<Integer> list;

	public ListElementsMoverTest() {

		this.list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
	}

	// -------------------- move to front -------------------- //

	@Test
	public void testMoveToFront() {

		createMover(6, 4, 2).moveToFront();
		assertList(6, 4, 2, 1, 3, 5);
	}

	@Test
	public void testMoveToFrontWithEmptyList() {

		list.clear();
		createMover(1, 2).moveToFront();
		assertList(1, 2);
	}

	@Test
	public void testMoveToFrontWithEmptySetOfMovingElements() {

		createMover().moveToFront();
		assertList(1, 2, 3, 4, 5, 6);
	}

	@Test
	public void testMoveToFrontWithNotContainedElements() {

		createMover(4, 7).moveToFront();
		assertList(4, 7, 1, 2, 3, 5, 6);
	}

	// -------------------- move to back -------------------- //

	@Test
	public void testMoveToBack() {

		createMover(2, 6, 4).moveToBack();
		assertList(1, 3, 5, 2, 6, 4);
	}

	@Test
	public void testMoveToBackWithEmptyList() {

		list.clear();
		createMover(1, 2).moveToBack();
		assertList(1, 2);
	}

	@Test
	public void testMoveToBackWithEmptySetOfMovingElements() {

		createMover().moveToBack();
		assertList(1, 2, 3, 4, 5, 6);
	}

	@Test
	public void testMoveToBackWithNotContainedElements() {

		createMover(4, 7).moveToBack();
		assertList(1, 2, 3, 5, 6, 4, 7);
	}

	// -------------------- move in front of -------------------- //

	@Test
	public void testMoveInFrontOf() {

		createMover(3, 6).moveInFrontOf(5);
		assertList(1, 2, 4, 3, 6, 5);
	}

	@Test
	public void testMoveInFrontOfWithEmptyList() {

		list.clear();
		createMover(1, 2).moveInFrontOf(3);
		assertList(1, 2, 3);
	}

	@Test
	public void testMoveInFrontOfWithEmptySetOfMovingElements() {

		createMover().moveInFrontOf(4);
		assertList(1, 2, 3, 4, 5, 6);
	}

	@Test
	public void testMoveInFrontOfWithNotContainedElements() {

		createMover(7, 5).moveInFrontOf(4);
		assertList(1, 2, 3, 7, 5, 4, 6);
	}

	@Test
	public void testMoveInFrontOfWithNotContainedAnchor() {

		createMover(3, 6).moveInFrontOf(7);
		assertList(1, 2, 4, 5, 3, 6, 7);
	}

	@Test
	public void testMoveInFrontOfWithMovingAnchor() {

		createMover(5, 3, 6).moveInFrontOf(3);
		assertList(1, 2, 5, 6, 3, 4);
	}

	// -------------------- move behind -------------------- //

	@Test
	public void testMoveBehind() {

		createMover(3, 5).moveBehind(1);
		assertList(1, 3, 5, 2, 4, 6);
	}

	@Test
	public void testMoveBehindWithEmptyList() {

		list.clear();
		createMover(1, 2).moveBehind(3);
		assertList(3, 1, 2);
	}

	@Test
	public void testMoveBehindWithEmptySetOfMovingElements() {

		createMover().moveBehind(4);
		assertList(1, 2, 3, 4, 5, 6);
	}

	@Test
	public void testMoveBehindWithNotContainedElements() {

		createMover(7, 5).moveBehind(4);
		assertList(1, 2, 3, 4, 7, 5, 6);
	}

	@Test
	public void testMoveBehindWithNotContainedAnchor() {

		createMover(3, 6).moveBehind(7);
		assertList(1, 2, 4, 5, 7, 3, 6);
	}

	@Test
	public void testMoveBehindWithMovingAnchor() {

		createMover(5, 3, 6).moveBehind(3);
		assertList(1, 2, 3, 5, 6, 4);
	}

	// -------------------- private -------------------- //

	private ListElementsMover<Integer> createMover(Integer...elements) {

		return new ListElementsMover<>(list, Arrays.asList(elements));
	}

	private void assertList(Integer...elements) {

		assertEquals(Arrays.asList(elements), list);
	}
}
