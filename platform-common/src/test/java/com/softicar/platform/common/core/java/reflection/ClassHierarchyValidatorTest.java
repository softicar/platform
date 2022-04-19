package com.softicar.platform.common.core.java.reflection;

import com.softicar.platform.common.testing.AbstractTest;
import java.io.Closeable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;

public class ClassHierarchyValidatorTest extends AbstractTest {

	// -------------------- assertNoInterfaces -------------------- //

	@Test
	public void testAssertNoInterfacesWithValidClassAndInterface() {

		new ClassHierarchyValidator(Object.class).assertNoInterfaces();
		new ClassHierarchyValidator(Serializable.class).assertNoInterfaces();
	}

	@Test(expected = ClassHierarchyValidationException.class)
	public void testAssertNoInterfacesWithIllegalClass() {

		new ClassHierarchyValidator(ArrayList.class).assertNoInterfaces();
	}

	@Test(expected = ClassHierarchyValidationException.class)
	public void testAssertNoInterfacesWithIllegalInterface() {

		new ClassHierarchyValidator(Closeable.class).assertNoInterfaces();
	}

	// -------------------- assertNoSuperClass -------------------- //

	@Test
	public void testAssertNoSuperClassWithValidClass() {

		new ClassHierarchyValidator(SomeClass.class).assertNoSuperClass();
	}

	@Test
	public void testAssertNoSuperClassWithInterface() {

		new ClassHierarchyValidator(List.class).assertNoSuperClass();
	}

	@Test(expected = ClassHierarchyValidationException.class)
	public void testAssertNoSuperClassWithIllegalClass() {

		new ClassHierarchyValidator(ArrayList.class).assertNoSuperClass();
	}

	// -------------------- assertOnlyOneInterface -------------------- //

	@Test
	public void testAssertOnlyOneInterfaceWithValidClassAndInterface() {

		new ClassHierarchyValidator(Closeable.class).assertOnlyOneInterface(AutoCloseable.class);
		new ClassHierarchyValidator(SomeClass.class).assertOnlyOneInterface(SomeInterface.class);
	}

	@Test(expected = ClassHierarchyValidationException.class)
	public void testAssertOnlyOneInterfaceWithIllegalClass() {

		new ClassHierarchyValidator(ArrayList.class).assertOnlyOneInterface(List.class);
	}

	@Test(expected = ClassHierarchyValidationException.class)
	public void testAssertOnlyOneInterfaceWithIllegalInterface() {

		new ClassHierarchyValidator(List.class).assertOnlyOneInterface(Collection.class);
	}

	// -------------------- test classes and interfaces -------------------- //

	private static interface SomeInterface {
		// nothing to add
	}

	private static class SomeClass implements SomeInterface {
		// nothing to add
	}
}
