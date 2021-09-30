package com.softicar.platform.common.container.iterable.concat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class ConcatIteratorTest extends Assert {

	private static final String A = "A";
	private static final String B = "B";
	private static final String C = "C";
	private final Iterator<String> iteratorA;
	private final Iterable<String> iterableA;
	private final Iterator<String> iteratorB;
	private final Iterable<String> iterableB;
	private final ConcatIterator<String> iterator;

	public ConcatIteratorTest() {

		this.iteratorA = Mockito.mock(Iterator.class);
		this.iterableA = () -> iteratorA;
		this.iteratorB = Mockito.mock(Iterator.class);
		this.iterableB = () -> iteratorB;
		this.iterator = new ConcatIterator<>(Arrays.asList(iterableA, iterableB));
	}

	@Test
	public void test() {

		List<String> list1 = Arrays.asList(A, B);
		List<String> list2 = Arrays.asList(C, B, A);
		ConcatIterator<String> iterator = new ConcatIterator<>(Arrays.asList(list1, list2));
		List<String> output = new ArrayList<>();
		while (iterator.hasNext()) {
			output.add(iterator.next());
		}
		assertEquals(Arrays.asList(A, B, C, B, A), output);
	}

	// ------------------------------ hasNext() ------------------------------ //

	/**
	 * When the first {@link Iterator} has elements, calling
	 * {@link ConcatIterator#hasNext()} must only call
	 * {@link Iterator#hasNext()} on it and nothing else.
	 * <p>
	 * For example, {@link Iterator#next()} may <b>not</b> be invoked on any
	 * {@link Iterator} and no other {@link Iterator} but the first may be
	 * touched.
	 */
	@Test
	public void testHasNext() {

		Mockito.when(iteratorA.hasNext()).thenReturn(true);
		Mockito.when(iteratorA.next()).thenReturn(A);

		boolean hasNext = iterator.hasNext();

		assertTrue(hasNext);
		Mockito.verify(iteratorA).hasNext();
		Mockito.verifyNoMoreInteractions(iteratorA, iteratorB);
	}

	@Test
	public void testHasNextWithEmptyFirstIterator() {

		Mockito.when(iteratorA.hasNext()).thenReturn(false);
		Mockito.when(iteratorB.hasNext()).thenReturn(true);

		boolean hasNext = iterator.hasNext();

		assertTrue(hasNext);

		InOrder inOrder = Mockito.inOrder(iteratorA, iteratorB);
		inOrder.verify(iteratorA).hasNext();
		inOrder.verify(iteratorB).hasNext();
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void testHasNextWithAllIteratorsEmpty() {

		Mockito.when(iteratorA.hasNext()).thenReturn(false);
		Mockito.when(iteratorB.hasNext()).thenReturn(false);

		boolean hasNext = iterator.hasNext();

		assertFalse(hasNext);

		InOrder inOrder = Mockito.inOrder(iteratorA, iteratorB);
		inOrder.verify(iteratorA).hasNext();
		inOrder.verify(iteratorB).hasNext();
		inOrder.verifyNoMoreInteractions();
	}

	// ------------------------------ next() ------------------------------ //

	/**
	 * Calling {@link Iterator#next()} on an iterator is legal, so we must
	 * ensure that this does not cause any problems.
	 */
	@Test
	public void testNextWithoutCallingHasNext() {

		Mockito.when(iteratorA.hasNext()).thenReturn(true);
		Mockito.when(iteratorA.next()).thenReturn(A);

		String next = iterator.next();

		assertEquals(A, next);

		InOrder inOrder = Mockito.inOrder(iteratorA, iteratorB);
		inOrder.verify(iteratorA).hasNext();
		inOrder.verify(iteratorA).next();
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void testNextWithoutCallingHasNextAndWithEmptyFirstIterator() {

		Mockito.when(iteratorA.hasNext()).thenReturn(false);
		Mockito.when(iteratorB.hasNext()).thenReturn(true);
		Mockito.when(iteratorB.next()).thenReturn(A);

		String next = iterator.next();

		assertEquals(A, next);

		InOrder inOrder = Mockito.inOrder(iteratorA, iteratorB);
		inOrder.verify(iteratorA).hasNext();
		inOrder.verify(iteratorB).hasNext();
		inOrder.verify(iteratorB).next();
		inOrder.verifyNoMoreInteractions();
	}
}
