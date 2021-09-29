package com.softicar.platform.common.core.utils.equals;

import org.junit.Assert;
import org.junit.Test;

public class EqualsComposerTest extends Assert {

	private static final String A = "A";
	private static final String B = "B";
	private final EqualsComposer<TestObject> composer;
	private final TestObject a1;
	private final TestObject b2;

	public EqualsComposerTest() {

		this.composer = new EqualsComposer<>(//
			new EqualsKeyExtractor<>(TestObject::getValue1),
			new EqualsKeyExtractor<>(TestObject::getValue2));
		this.a1 = new TestObject(A, 1);
		this.b2 = new TestObject(B, 2);
	}

	@Test
	public void testWithSameObject() {

		assertTrue(composer.compare(a1, a1));
		assertTrue(composer.compare(null, null));
	}

	@Test
	public void testWithEqualObject() {

		assertTrue(composer.compare(a1, new TestObject(A, 1)));
		assertTrue(composer.compare(b2, new TestObject(B, 2)));
	}

	@Test
	public void testWithNonEqualObject() {

		assertFalse(composer.compare(a1, new TestObject(B, 1)));
		assertFalse(composer.compare(a1, new TestObject(A, 2)));
	}

	@Test
	public void testWithNull() {

		assertFalse(composer.compare(a1, null));
		assertFalse(composer.compare(null, a1));
	}

	private static class TestObject {

		public String value1;
		public Integer value2;

		public TestObject(String value1, Integer value2) {

			this.value1 = value1;
			this.value2 = value2;
		}

		public String getValue1() {

			return value1;
		}

		public Integer getValue2() {

			return value2;
		}
	}
}
