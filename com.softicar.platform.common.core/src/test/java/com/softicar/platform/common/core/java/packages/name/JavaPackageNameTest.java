package com.softicar.platform.common.core.java.packages.name;

import org.junit.Assert;
import org.junit.Test;

public class JavaPackageNameTest extends Assert {

	@Test
	public void testGetName() {

		assertEquals("com.example.foo", new JavaPackageName("com.example.foo").getName());
		assertEquals("com.example", new JavaPackageName("com.example").getName());
		assertEquals("com", new JavaPackageName("com").getName());
	}

	@Test
	public void testGetSimpleName() {

		assertEquals("foo", new JavaPackageName("com.example.foo").getSimpleName());
		assertEquals("example", new JavaPackageName("com.example").getSimpleName());
		assertEquals("com", new JavaPackageName("com").getSimpleName());
	}

	@Test
	public void testGetParent() {

		assertEquals(new JavaPackageName("com.example"), new JavaPackageName("com.example.foo").getParent());
		assertEquals(new JavaPackageName("com"), new JavaPackageName("com.example").getParent());
		assertSame(JavaPackageName.getEmpty(), new JavaPackageName("com").getParent());
	}

	@Test
	public void testIsChildOf() {

		JavaPackageName child = new JavaPackageName("com.example.foo");

		assertTrue(child.isChildOf(JavaPackageName.getEmpty()));
		assertTrue(child.isChildOf(new JavaPackageName("com")));
		assertTrue(child.isChildOf(new JavaPackageName("com.example")));
		assertFalse(child.isChildOf(new JavaPackageName("com.example.foo")));
	}

	@Test
	public void testIsParentOf() {

		JavaPackageName parent = new JavaPackageName("com.example.foo");

		assertTrue(JavaPackageName.getEmpty().isParentOf(parent));
		assertTrue(new JavaPackageName("com").isParentOf(parent));
		assertTrue(new JavaPackageName("com.example").isParentOf(parent));
		assertFalse(new JavaPackageName("com.example.foo").isParentOf(parent));
	}
}
