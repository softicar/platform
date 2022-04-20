package com.softicar.platform.common.container.list;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class ListFactoryTest extends AbstractTest {

	@Test
	public void createEmptyArrayList() {

		assertEquals(0, ListFactory.createArrayList().size());
	}

	@Test
	public void copiesArrayListElements() {

		ArrayList<Integer> list = ListFactory.createArrayList(Arrays.asList(1, 2, 4));
		assertEquals(3, list.size());
		assertEquals(1, list.get(0).intValue());
		assertEquals(2, list.get(1).intValue());
		assertEquals(4, list.get(2).intValue());
	}
}
