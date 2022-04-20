package com.softicar.platform.common.container.map.number;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link IntegerMap}.
 *
 * @author Oliver Richers
 */
public class IntegerMapTest extends AbstractTest {

	private IntegerMap<String> map;

	@Before
	public void before() {

		this.map = new IntegerMap<>();
	}

	@Test
	public void returnsCorrectDefaultValue() {

		assertEquals(0, map.get("foo").intValue());
		assertEquals(0, map.get("bar").intValue());
	}

	@Test
	public void addsCorrectly() {

		map.add("foo", 7);
		map.add("foo", 10);
		assertEquals(17, map.get("foo").intValue());
	}

	@Test
	public void subtractsCorrectly() {

		map.sub("foo", 7);
		map.sub("foo", 10);
		assertEquals(-17, map.get("foo").intValue());
	}

	@Test
	public void incrementsAndDecrementsCorrectly() {

		map.inc("foo");
		map.dec("foo");
		map.dec("foo");
		map.inc("foo");
		map.inc("foo");
		map.inc("foo");
		map.inc("foo");
		map.dec("foo");
		assertEquals(2, map.get("foo").intValue());
	}
}
