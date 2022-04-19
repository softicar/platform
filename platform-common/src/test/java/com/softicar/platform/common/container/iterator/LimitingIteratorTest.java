package com.softicar.platform.common.container.iterator;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

public class LimitingIteratorTest extends AbstractTest {

	private final List<Integer> list;

	public LimitingIteratorTest() {

		this.list = Arrays.asList(1, 2, 3, 4, 5);
	}

	@Test
	public void testWithoutOffsetAndWithoutLimit() {

		assertValues(new LimitingIterator<>(list.iterator(), 0, 0), 1, 2, 3, 4, 5);
	}

	@Test
	public void testWithoutOffsetAndWithLimit() {

		assertValues(new LimitingIterator<>(list.iterator(), 0, 3), 1, 2, 3);
	}

	@Test
	public void testWithOffsetAndWithoutLimit() {

		assertValues(new LimitingIterator<>(list.iterator(), 2, 0), 3, 4, 5);
	}

	@Test
	public void testWithOffsetAndWithLimit() {

		assertValues(new LimitingIterator<>(list.iterator(), 2, 2), 3, 4);
	}

	@Test
	public void testWithOutOfRangeOffset() {

		assertValues(new LimitingIterator<>(list.iterator(), 8, 0));
	}

	private void assertValues(Iterator<Integer> iterator, Integer...expectedValues) {

		List<Integer> values = new ArrayList<>();
		iterator.forEachRemaining(values::add);
		assertEquals(Arrays.asList(expectedValues), values);
	}
}
