package com.softicar.platform.common.core.interfaces;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class BiConsumersTest extends AbstractTest {

	private static final String A = "A";
	private final ArrayList<String> someList;

	public BiConsumersTest() {

		this.someList = new ArrayList<>();
	}

	@Test
	public void testBindFirst() {

		BiConsumers.bindFirst(BiConsumersTest::add, someList).accept(A);

		assertEquals(1, someList.size());
		assertEquals(A, someList.get(0));
	}

	@Test
	public void testBindSecond() {

		BiConsumers.bindSecond(BiConsumersTest::add, A).accept(someList);

		assertEquals(1, someList.size());
		assertEquals(A, someList.get(0));
	}

	private static void add(List<String> list, String element) {

		list.add(element);
	}
}
