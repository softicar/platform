package com.softicar.platform.common.core.utils;

import java.lang.reflect.Constructor;
import org.junit.Assert;
import org.junit.Test;

public class ReflectionUtilsTest extends Assert {

	@Test(expected = RuntimeException.class)
	public void testException() {

		DevNull.swallow(new TestClass());
		DevNull.swallow(new TestClass("foo"));
	}

	@Test
	public void testExceptionInConstructorUsingInstantiationByClass() {

		try {
			ReflectionUtils.newInstance(TestClass.class);
			fail();
		} catch (RuntimeException exception) {
			assertSame(RuntimeException.class, exception.getClass());
			assertEquals("foo", exception.getMessage());
		}
	}

	@Test
	public void testExceptionInConstructorUsingInstantiationByConstructor() {

		try {
			Constructor<TestClass> constructor = ReflectionUtils.getConstructorOrNull(TestClass.class);
			ReflectionUtils.newInstance(constructor);
			fail();
		} catch (RuntimeException exception) {
			assertSame(RuntimeException.class, exception.getClass());
			assertEquals("foo", exception.getMessage());
		}
	}

	@Test
	public void testExceptionInConstructorUsingInstantiationByConstructorWithParameter() {

		try {
			Constructor<TestClass> constructor = ReflectionUtils.getConstructorOrNull(TestClass.class, String.class);
			ReflectionUtils.newInstance(constructor, "hello");
			fail();
		} catch (RuntimeException exception) {
			assertSame(RuntimeException.class, exception.getClass());
			assertEquals("hello", exception.getMessage());
		}
	}

	private static class TestClass {

		public TestClass() {

			throw new RuntimeException("foo");
		}

		public TestClass(String message) {

			throw new RuntimeException(message);
		}
	}
}
