package com.softicar.platform.common.container.pair;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Map.Entry;
import org.junit.Test;

public class PairFactoryTest extends AbstractTest {

	@Test
	public void createsPairFromElements() {

		Pair<String, Integer> pair = PairFactory.create("foo", 1337);

		assertEquals("foo", pair.getFirst());
		assertEquals(1337, pair.getSecond().intValue());
	}

	@Test
	public void createsPairFromMapEntry() {

		Entry<String, Integer> entry = new Entry<>() {

			@Override
			public String getKey() {

				return "foo";
			}

			@Override
			public Integer getValue() {

				return 1337;
			}

			@Override
			public Integer setValue(Integer value) {

				throw new UnsupportedOperationException();
			}
		};
		Pair<String, Integer> pair = PairFactory.create(entry);

		assertEquals("foo", pair.getFirst());
		assertEquals(1337, pair.getSecond().intValue());
	}
}
