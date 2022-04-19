package com.softicar.platform.common.core.utils.equals;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class EqualsComparerBaseTest extends AbstractTest {

	@Test
	public void testWithDerivedObject() {

		TestObject object = new TestObject();
		TestObject derived = new DerivedTestObject();

		assertTrue(new Comparator(true).compareToObject(object, derived));
		assertFalse(new Comparator(false).compareToObject(object, derived));
	}

	@Test
	public void testWithNonComparableObjects() {

		TestObject object = new TestObject();

		assertFalse(new ThrowingComparator().compareToObject(object, null));
		assertFalse(new ThrowingComparator().compareToObject(object, "x"));
		assertFalse(new ThrowingComparator().compareToObject(object, 123));
		assertFalse(new ThrowingComparator().compareToObject(new DerivedTestObject(), object));
	}

	@Test
	public void testWithNull() {

		assertTrue(new ThrowingComparator().compareToObject(null, null));
	}

	private static class ThrowingComparator extends EqualsComparerBase<TestObject> {

		@Override
		public boolean compare(TestObject a, TestObject b) {

			throw new AssertionError("Unexpected call of compare method.");
		}
	}

	private static class Comparator extends EqualsComparerBase<TestObject> {

		private final boolean value;

		public Comparator(boolean value) {

			this.value = value;
		}

		@Override
		public boolean compare(TestObject a, TestObject b) {

			return value;
		}
	}

	private static class TestObject {

		// nothing to add
	}

	private static class DerivedTestObject extends TestObject {

		// nothing to add
	}
}
