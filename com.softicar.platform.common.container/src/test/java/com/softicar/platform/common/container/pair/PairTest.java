package com.softicar.platform.common.container.pair;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Objects;
import org.junit.Test;

public class PairTest {

	private final Pair<String, Integer> pair = PairFactory.create("foo", 1337);
	private final Pair<?, ?> nullPair = PairFactory.create(null, null);

	@Test
	public void convertsCorrectlyToString() {

		assertEquals("[foo, 1337]", pair.toString());
	}

	@Test
	public void comparesCorrectlyWithEquals() {

		assertFalse(nullPair.equals(pair));
		assertFalse(pair.equals(nullPair));

		assertTrue(pair.equals(pair));
		assertTrue(nullPair.equals(nullPair));

		assertTrue(pair.equals(PairFactory.create("foo", 1337)));
		assertFalse(pair.equals(PairFactory.create("bar", 1337)));
	}

	@Test
	public void supportsNullElements() {

		assertEquals(null, nullPair.getFirst());
		assertEquals(null, nullPair.getSecond());
	}

	@Test
	public void supportsToStringWithNullElements() {

		assertEquals("[null, null]", nullPair.toString());
	}

	@Test
	public void supportsHashCodeWithNullElements() {

		assertEquals(Objects.hash(null, null), nullPair.hashCode());
		assertEquals(Objects.hash(33, null), PairFactory.create(33, null).hashCode());
		assertEquals(Objects.hash(null, 42), PairFactory.create(null, 42).hashCode());
	}

	@Test
	public void supportsEqualsWithNull() {

		assertFalse(nullPair.equals(null));
		assertFalse(pair.equals(null));
	}
}
