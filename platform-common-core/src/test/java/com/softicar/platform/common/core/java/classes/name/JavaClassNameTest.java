package com.softicar.platform.common.core.java.classes.name;

import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import org.junit.Assert;
import org.junit.Test;

public class JavaClassNameTest extends Assert {

	// ------------------------------ String Constructor ------------------------------ //

	@Test
	public void testStringConstructorWithSlashes() {

		JavaClassName name = new JavaClassName("foo/bar/Baz");

		assertEquals("Baz", name.getSimpleName());
		assertEquals("foo.bar.Baz", name.getName());
		assertEquals("foo.bar.Baz", name.getCanonicalName());
		assertEquals(new JavaPackageName("foo.bar"), name.getPackageName());
	}

	@Test
	public void testStringConstructorWithNormalClass() {

		JavaClassName name = new JavaClassName("foo.bar.Baz");

		assertEquals("Baz", name.getSimpleName());
		assertEquals("foo.bar.Baz", name.getName());
		assertEquals("foo.bar.Baz", name.getCanonicalName());
		assertEquals(new JavaPackageName("foo.bar"), name.getPackageName());
	}

	@Test
	public void testStringConstructorWithInnerClass() {

		JavaClassName name = new JavaClassName("foo.bar.Baz$Boo$Loo");

		assertEquals("Loo", name.getSimpleName());
		assertEquals("foo.bar.Baz$Boo$Loo", name.getName());
		assertEquals("foo.bar.Baz.Boo.Loo", name.getCanonicalName());
		assertEquals(new JavaPackageName("foo.bar"), name.getPackageName());
	}

	@Test
	public void testStringConstructorWithoutPackage() {

		JavaClassName name = new JavaClassName("Foo");

		assertEquals("Foo", name.getSimpleName());
		assertEquals("Foo", name.getName());
		assertEquals("Foo", name.getCanonicalName());
		assertEquals(new JavaPackageName(""), name.getPackageName());
	}

	// ------------------------------ Class Constructor ------------------------------ //

	@Test
	public void testClassConstructorWithPrimitiveClass() {

		JavaClassName name = new JavaClassName(byte.class);

		assertEqualsThreeWay("byte", byte.class.getSimpleName(), name.getSimpleName());
		assertEqualsThreeWay("byte", byte.class.getName(), name.getName());
		assertEqualsThreeWay("byte", byte.class.getCanonicalName(), name.getCanonicalName());
		assertEquals(new JavaPackageName(""), name.getPackageName());
	}

	@Test
	public void testClassConstructorWithArrayClass() {

		JavaClassName name = new JavaClassName(String[].class);

		assertEqualsThreeWay("String[]", String[].class.getSimpleName(), name.getSimpleName());
		assertEqualsThreeWay("[Ljava.lang.String;", String[].class.getName(), name.getName());
		assertEqualsThreeWay("java.lang.String[]", String[].class.getCanonicalName(), name.getCanonicalName());
		assertEquals(new JavaPackageName(""), name.getPackageName());
	}

	@Test
	public void testClassConstructorWithPrimitiveArrayClass() {

		JavaClassName name = new JavaClassName(byte[].class);

		assertEquals("byte[]", byte[].class.getSimpleName(), name.getSimpleName());
		assertEquals("byte[]", byte[].class.getName(), name.getName());
		assertEquals("byte[]", byte[].class.getCanonicalName(), name.getCanonicalName());
		assertEquals(new JavaPackageName(""), name.getPackageName());
	}

	// ------------------------------ utilities ------------------------------ //

	private void assertEqualsThreeWay(Object expected, Object reference, Object actual) {

		assertEquals("reference implementation", expected, reference);
		assertEquals(expected, actual);
	}
}
