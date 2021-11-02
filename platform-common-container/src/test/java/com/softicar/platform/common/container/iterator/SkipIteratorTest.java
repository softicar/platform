package com.softicar.platform.common.container.iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.softicar.platform.common.core.annotations.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

/**
 * Test cases for {@link SkipIterator}.
 *
 * @author Oliver Richers
 */
public class SkipIteratorTest {

	@Test
	public void supportsEmptyCollections() {

		SkipIterator<@Nullable String> iterator = new SkipIterator<>(new ArrayList<>(), "one");
		assertFalse(iterator.hasNext());
	}

	@Test
	public void supportsVirtuallyEmptyCollections() {

		SkipIterator<@Nullable String> iterator = new SkipIterator<>(Arrays.asList("one", "one"), "one");
		assertFalse(iterator.hasNext());
	}

	@Test
	public void supportsNullElementsInContainers() {

		SkipIterator<@Nullable String> iterator = new SkipIterator<>(Arrays.asList("one", null, "one"), "one");
		assertTrue(iterator.hasNext());
		assertNull(iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void supportsNullAsSkipElement() {

		SkipIterator<@Nullable String> iterator = new SkipIterator<>(Arrays.asList("one", null), null);
		assertTrue(iterator.hasNext());
		assertEquals("one", iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void skipsMultipleTimes() {

		SkipIterator<@Nullable String> iterator = new SkipIterator<>(Arrays.asList("one", "two", "one", "three", "one"), "one");
		assertTrue(iterator.hasNext());
		assertEquals("two", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("three", iterator.next());
		assertFalse(iterator.hasNext());
	}
}
