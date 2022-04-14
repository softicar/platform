package com.softicar.platform.common.container.iterator;

import com.softicar.platform.common.core.annotations.Nullable;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

/**
 * Test cases for {@link MergeIterator}.
 *
 * @author Oliver Richers
 */
public class MergeIteratorTest extends AbstractTest {

	@Test
	public void mergesEmptyContainers() {

		MergeIterator<@Nullable String> iterator = new MergeIterator<>(new ArrayList<>(), new ArrayList<>());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void mergesEmptyWithNonEmptyContainers() {

		MergeIterator<@Nullable String> iterator = new MergeIterator<>(new ArrayList<>(), Arrays.asList("one"));
		assertTrue(iterator.hasNext());
		assertEquals("one", iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void mergesContainersOnCorrectOrder() {

		MergeIterator<@Nullable String> iterator = new MergeIterator<>(Arrays.asList("one"), Arrays.asList("two"));
		assertTrue(iterator.hasNext());
		assertEquals("one", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("two", iterator.next());
		assertFalse(iterator.hasNext());
	}
}
