package com.softicar.platform.common.code.classpath.metadata;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import org.junit.Test;

public class TransitivelyExtendingClassesFinderTest extends AbstractTest {

	private final ClasspathFilesTestMetadata metadata;

	public TransitivelyExtendingClassesFinderTest() {

		this.metadata = new ClasspathFilesTestMetadata();

		metadata.addExtendingClass(AbstractList.class, AbstractCollection.class);
		metadata.addExtendingClass(ArrayList.class, AbstractList.class);
		metadata.addExtendingClass(Vector.class, AbstractList.class);
		metadata.addExtendingClass(Stack.class, Vector.class);
	}

	@Test
	public void testWithRootClass() {

		Set<Class<?>> result = new TransitivelyExtendingClassesFinder(metadata).findAll(AbstractCollection.class);

		assertEquals(
			new HashSet<>(
				Arrays
					.asList(//
						AbstractList.class,
						ArrayList.class,
						Stack.class,
						Vector.class)),
			result);
	}

	@Test
	public void testWithNonExtendedClass() {

		Set<Class<?>> result = new TransitivelyExtendingClassesFinder(metadata).findAll(Stack.class);

		assertTrue(result.isEmpty());
	}
}
