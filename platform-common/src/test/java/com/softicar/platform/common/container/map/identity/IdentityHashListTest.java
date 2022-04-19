package com.softicar.platform.common.container.map.identity;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class IdentityHashListTest extends AbstractTest {

	private static final String A = "A";
	private static final String B = "B";
	private static final String C = "C";

	@Test
	public void testAdd() {

		IdentityHashList<String> list = new IdentityHashList<>();
		list.add(A);
		list.add(A);
		list.add(B);
		list.add(A);
		list.add(B);
		list.add(C);
		assertEquals(3, list.size());
		assertEquals(A, list.get(0));
		assertEquals(B, list.get(1));
		assertEquals(C, list.get(2));
	}

	@Test
	public void testRemoveWithIndex() {

		IdentityHashList<String> list = new IdentityHashList<>();
		list.add(A);
		list.add(B);
		list.remove(0);
		assertEquals(1, list.size());
		assertEquals(B, list.get(0));
	}

	@Test
	public void testRemoveWithObject() {

		IdentityHashList<String> list = new IdentityHashList<>();
		list.add(A);
		list.add(B);
		list.remove(A);
		assertEquals(1, list.size());
		assertEquals(B, list.get(0));
	}

	@Test
	public void testReverse() {

		IdentityHashList<String> list = new IdentityHashList<>();
		list.add(A);
		list.add(B);
		list.add(C);

		list.reverse();

		assertEquals(3, list.size());
		assertEquals(C, list.get(0));
		assertEquals(B, list.get(1));
		assertEquals(A, list.get(2));
	}
}
