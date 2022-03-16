package com.softicar.platform.common.core.java.classes.name;

import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

public class JavaClassNameDeterminerTest extends Assert {

	private final JavaClassNameDeterminer determiner;

	public JavaClassNameDeterminerTest() {

		this.determiner = new JavaClassNameDeterminer();
	}

	@Test
	public void testFromFieldDescriptorWithClass() {

		var className = determineFromFieldDescriptor("Lfoo/bar/Baz;");
		assertTrue(className.isPresent());
		assertEquals("foo.bar.Baz", className.get().getName());
	}

	@Test
	public void testFromFieldDescriptorWithClassArray() {

		var className = determineFromFieldDescriptor("[Lfoo/bar/Baz;");
		assertTrue(className.isPresent());
		assertEquals("foo.bar.Baz", className.get().getName());
	}

	@Test
	public void testFromFieldDescriptorInteger() {

		var className = determineFromFieldDescriptor("I");
		assertFalse(className.isPresent());
	}

	@Test
	public void testFromFieldDescriptorWithIntegerArray() {

		var className = determineFromFieldDescriptor("[I");
		assertFalse(className.isPresent());
	}

	@Test
	public void testFromFieldDescriptorWithEmptyString() {

		var className = determineFromFieldDescriptor("");
		assertFalse(className.isPresent());
	}

	@Test(expected = NullPointerException.class)
	public void testFromFieldDescriptorWithNull() {

		determineFromFieldDescriptor(null);
	}

	private Optional<JavaClassName> determineFromFieldDescriptor(String fieldDescriptor) {

		return determiner.fromFieldDescriptor(fieldDescriptor);
	}
}
