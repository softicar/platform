package com.softicar.platform.common.core.utils.equals;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class EqualsKeyExtractorTest extends AbstractTest {

	private static final String A = "A";
	private static final String B = "B";
	private final EqualsKeyExtractor<TestObject, String> keyExtractor;
	private final TestObject a;
	private final TestObject b;

	public EqualsKeyExtractorTest() {

		this.keyExtractor = new EqualsKeyExtractor<>(TestObject::getValue);
		this.a = new TestObject(A);
		this.b = new TestObject(B);
	}

	@Test
	public void testWithSameObject() {

		assertTrue(keyExtractor.compare(a, a));
		assertTrue(keyExtractor.compare(b, b));
		assertTrue(keyExtractor.compare(null, null));
	}

	@Test
	public void testWithEqualObjects() {

		assertTrue(keyExtractor.compare(a, new TestObject(A)));
	}

	@Test
	public void testWithNonEqualObjects() {

		assertFalse(keyExtractor.compare(a, b));
	}

	@Test
	public void testWithNull() {

		assertFalse(keyExtractor.compare(a, null));
		assertFalse(keyExtractor.compare(null, a));
	}

	private static class TestObject {

		public String value;

		public TestObject(String value) {

			this.value = value;
		}

		public String getValue() {

			return value;
		}
	}
}
