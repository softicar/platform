package com.softicar.platform.common.container.set.integer;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link IntSet}.
 *
 * @author Oliver Richers
 */
public class IntSetTest extends AbstractTest {

	private IntSet set;

	@Before
	public void setUp() {

		set = new IntSet();
	}

	@Test
	public void startsEmpty() {

		assertTrue(set.isEmpty());
		assertFalse(set.iterator().hasNext());
		assertEquals(0, set.size());
	}

	@Test
	public void returnsCorrectSize() {

		set.add(1);

		assertFalse(set.isEmpty());
		assertTrue(set.iterator().hasNext());
		assertEquals(1, set.size());
	}

	@Test
	public void returnsNullIfIntNotContained() {

		set.add(2);

		assertNull(set.get(42));
		assertNull(set.get(1337));
	}

	@Test
	public void returnsCorrectIntIfContained() {

		set.add(42);
		set.add(1337);

		assertEquals(Integer.valueOf(42), set.get(42));
		assertEquals(Integer.valueOf(1337), set.get(1337));
	}

	@Test
	public void treatsMinimumIntAsNull() {

		set.add(Integer.MIN_VALUE);

		assertEquals(1, set.size());
		assertNull(set.get(Integer.MIN_VALUE));
	}
}
