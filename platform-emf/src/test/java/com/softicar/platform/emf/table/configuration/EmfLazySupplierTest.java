package com.softicar.platform.emf.table.configuration;

import org.junit.Assert;
import org.junit.Test;

public class EmfLazySupplierTest extends Assert {

	private static final String A = "A";
	private static final String B = "B";

	@Test
	public void testGetWithNormalFactory() {

		EmfLazySupplier<TestObject> supplier = new EmfLazySupplier<>(() -> new TestObject(A), o -> o.setName(B));

		TestObject object = supplier.get();

		assertNotNull(object);
		assertEquals(B, object.getName());
	}

	@Test
	public void testGetWithParameterizedFactory() {

		EmfLazySupplier<TestObject> supplier = new EmfLazySupplier<>(A, TestObject::new, o -> o.setName(B));

		TestObject object = supplier.get();

		assertNotNull(object);
		assertEquals(B, object.getName());
	}

	@Test
	public void testGetWithRepeatedCalls() {

		EmfLazySupplier<TestObject> supplier = new EmfLazySupplier<>(A, TestObject::new, o -> o.setName(B));

		TestObject object1 = supplier.get();
		TestObject object2 = supplier.get();
		TestObject object3 = supplier.get();

		assertNotNull(object1);
		assertSame(object1, object2);
		assertSame(object1, object3);
	}

	private static class TestObject {

		private String name;

		public TestObject(String name) {

			this.name = name;
		}

		public String getName() {

			return name;
		}

		public void setName(String name) {

			this.name = name;
		}
	}
}
