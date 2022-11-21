package com.softicar.platform.common.container.map.index;

import com.softicar.platform.common.testing.Asserts;
import java.util.List;
import org.junit.Test;

public class IndexHashMapTest extends Asserts {

	private static final List<String> EXAMPLE_LIST = List.of("zero", "one", "two", "three");

	@Test
	public void testConstructor() {

		var map = new IndexHashMap<>(EXAMPLE_LIST);

		assertEquals(0, map.getIndex("zero"));
		assertEquals(1, map.getIndex("one"));
		assertEquals(2, map.getIndex("two"));
		assertEquals(3, map.getIndex("three"));
	}

	@Test
	public void testAdd() {

		var map = new IndexHashMap<>(EXAMPLE_LIST);

		assertEquals(2, map.add("two"));
		assertEquals(4, map.add("four"));
	}
}
