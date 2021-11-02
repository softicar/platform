package com.softicar.platform.common.io.serialization.json;

import org.junit.Assert;
import org.junit.Test;

public class JsonBlockTest extends Assert {

	private final JsonBlock block;

	public JsonBlockTest() {

		this.block = new JsonBlock();
	}

	@Test
	public void testAddWithStringValue() {

		block.add("foo", "bar");
		assertEquals("{\"foo\":\"bar\"}", block.toString());
	}

	@Test(expected = NullPointerException.class)
	public void testAddWithStringValueNull() {

		block.add("foo", (String) null);
	}

	@Test
	public void testAddWithIntegerValue() {

		block.add("foo", Integer.valueOf(123));
		assertEquals("{\"foo\":123}", block.toString());
	}

	@Test(expected = NullPointerException.class)
	public void testAddWithIntegerValueNull() {

		block.add("foo", (Integer) null);
	}

	@Test(expected = NullPointerException.class)
	public void testAddWithNullAttribute() {

		block.add(null, Integer.valueOf(123));
	}

	@Test
	public void testToStringWithoutValues() {

		assertEquals("{}", block.toString());
	}

	@Test
	public void testAddAllWithStringValues() {

		block.addAll("foo", "bar", "baz");
		assertEquals("{\"foo\":[\"bar\",\"baz\"]}", block.toString());
	}

	@Test
	public void testAddAllWithIntegerValues() {

		block.addAll("foo", Integer.valueOf(10), Integer.valueOf(20));
		assertEquals("{\"foo\":[10,20]}", block.toString());
	}

	@Test
	public void testAddAllWithMixedValues() {

		block.addAll("foo", Integer.valueOf(10), "bar");
		assertEquals("{\"foo\":[10,\"bar\"]}", block.toString());
	}

	@Test(expected = NullPointerException.class)
	public void testAddAllWithValuesNullArray() {

		block.addAll("foo", (Object[]) null);
	}

	@Test(expected = NullPointerException.class)
	public void testAddAllWithValuesNullArrayElement() {

		block.addAll("foo", "bar", null);
	}

	@Test(expected = NullPointerException.class)
	public void testAddAllWithNullAttribute() {

		block.addAll(null, "bar", "baz");
	}

	@Test
	public void testToString() {

		block.add("Some String", "foo");
		block.add("Some Integer", Integer.valueOf(123));
		block.addAll("More Values", "bar", 456);
		block.addAll("No Values");
		block.add("Sub Block", new JsonBlock().add("subAttribute", "subValue"));

		assertEquals(//
			"{\"Some String\":\"foo\",\"Some Integer\":123,\"More Values\":[\"bar\",456],\"No Values\":[],\"Sub Block\":{\"subAttribute\":\"subValue\"}}",
			block.toString());
	}
}
