package com.softicar.platform.common.container.filter;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;
import org.junit.Test;

public class FilteringIteratorTest extends AbstractTest {

	private static final String A = "A";
	private static final String B = "B";
	private static final String C = "C";
	private final Iterator<String> originalIterator;
	private final Iterator<String> iterator;
	private Predicate<String> predicate;

	public FilteringIteratorTest() {

		this.originalIterator = Arrays.asList(A, B, C).iterator();
		this.iterator = new FilteringIterator<>(originalIterator, x -> predicate.test(x));
		this.predicate = x -> true;
	}

	@Test
	public void testWithDiscardingAll() {

		this.predicate = x -> false;

		assertFalse(iterator.hasNext());
	}

	@Test
	public void testWithFilteringForA() {

		this.predicate = x -> x == A;

		assertTrue(iterator.hasNext());
		assertEquals("A", iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testWithFilteringForC() {

		this.predicate = x -> x == C;

		assertTrue(iterator.hasNext());
		assertEquals("C", iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testHasNext() {

		boolean hasNext = iterator.hasNext();

		assertTrue(hasNext);

		// check that the original iterator was
		// advanced to the next valid element
		assertTrue(originalIterator.hasNext());
		assertSame(B, originalIterator.next());
	}

	@Test
	public void testNext() {

		String element = iterator.next();

		assertSame(A, element);

		// check that the original iterator was
		// not advanced beyond the second element
		assertTrue(originalIterator.hasNext());
		assertSame(B, originalIterator.next());
	}
}
