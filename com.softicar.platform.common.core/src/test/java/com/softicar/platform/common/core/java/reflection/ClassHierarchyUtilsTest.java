package com.softicar.platform.common.core.java.reflection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class ClassHierarchyUtilsTest extends Assert {

	@Test
	public void testGetAllInterfaces() {

		Set<Class<?>> interfaces = ClassHierarchyUtils.getAllInterfaces(TestClassB.class);

		assertEquals(2, interfaces.size());
		assertTrue(interfaces.contains(InterfaceA.class));
		assertTrue(interfaces.contains(InterfaceB.class));
	}

	@Test
	public void testForEachClassInHierarchy() {

		Collection<String> classNames = new ArrayList<>();

		ClassHierarchyUtils.forEachClassInHierarchy(TestClassB.class, javaClass -> classNames.add(javaClass.getSimpleName()));

		assertEquals("[TestClassB, TestClassA, Object]", classNames.toString());
	}

	// -------------------- test classes and interfaces -------------------- //

	private static interface InterfaceA {
		// nothing to add
	}

	private static interface InterfaceB extends InterfaceA {
		// nothing to add
	}

	private static class TestClassA implements InterfaceB {
		// nothing to add
	}

	private static class TestClassB extends TestClassA {
		// nothing to add
	}
}
