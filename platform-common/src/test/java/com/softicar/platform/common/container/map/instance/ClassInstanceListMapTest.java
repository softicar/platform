package com.softicar.platform.common.container.map.instance;

import com.softicar.platform.common.testing.Asserts;
import java.util.List;
import org.junit.Test;

public class ClassInstanceListMapTest extends Asserts {

	@Test
	public void test() {

		var map = new ClassInstanceListMap();

		map.add(1);
		map.add(2);
		map.add(3);

		map.add("foo");
		map.add("bar");
		map.add("baz");

		List<Integer> integerList = map.getInstances(Integer.class);
		assertEquals("[1, 2, 3]", integerList.toString());

		List<String> stringList = map.getInstances(String.class);
		assertEquals("[foo, bar, baz]", stringList.toString());
	}
}
