package com.softicar.platform.common.code.classpath.metadata;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import org.junit.Test;

public class TransitivelyImplementingClassesFinderTest extends AbstractTest {

	private final ClasspathFilesTestMetadata metadata;

	public TransitivelyImplementingClassesFinderTest() {

		this.metadata = new ClasspathFilesTestMetadata();

		metadata.addImplementingClass(Collection.class, Iterable.class);
		metadata.addImplementingClass(List.class, Collection.class);
		metadata.addImplementingClass(Set.class, Collection.class);
		metadata.addImplementingClass(AbstractList.class, List.class);
		metadata.addImplementingClass(ArrayList.class, List.class);
		metadata.addImplementingClass(Vector.class, List.class);

		metadata.addExtendingClass(ArrayList.class, AbstractList.class);
		metadata.addExtendingClass(Vector.class, AbstractList.class);
		metadata.addExtendingClass(Stack.class, Vector.class);
	}

	@Test
	public void testWithBasicInterface() {

		Set<Class<?>> result = new TransitivelyImplementingClassesFinder(metadata).findAll(Collection.class);

		assertEquals(
			new HashSet<>(
				Arrays
					.asList(//
						AbstractList.class,
						ArrayList.class,
						List.class,
						Set.class,
						Stack.class,
						Vector.class)),
			result);
	}

	@Test
	public void testWithClass() {

		Set<Class<?>> result = new TransitivelyImplementingClassesFinder(metadata).findAll(ArrayList.class);

		assertTrue(result.isEmpty());
	}

	@Test
	public void testWithBaseClass() {

		Set<Class<?>> result = new TransitivelyImplementingClassesFinder(metadata).findAll(Vector.class);

		assertTrue(result.isEmpty());
	}
}
