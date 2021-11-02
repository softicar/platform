package com.softicar.platform.emf.module.extension;

import com.softicar.platform.common.core.java.reflection.ClassHierarchyValidationException;
import java.util.Collection;
import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfModuleExtensionRegistryTest extends Assert {

	private final EmfModuleExtensionRegistry registry;

	public EmfModuleExtensionRegistryTest() {

		this.registry = new EmfModuleExtensionRegistry();
	}

	@Test(expected = ClassHierarchyValidationException.class)
	public void testRegisterExtensionWithIllegalInterface() {

		ISomeDerivedExtension extension = Mockito.mock(ISomeDerivedExtension.class);
		registry.registerExtension(ISomeDerivedExtension.class, extension);
	}

	@Test
	public void testGetExtensions() {

		ISomeExtension extension1 = Mockito.mock(ISomeExtension.class);
		ISomeExtension extension2 = Mockito.mock(ISomeExtension.class);
		registry.registerExtension(ISomeExtension.class, extension1);
		registry.registerExtension(ISomeExtension.class, extension2);

		Collection<ISomeExtension> extensions = registry.getExtensions(ISomeExtension.class);
		assertEquals(2, extensions.size());

		Iterator<ISomeExtension> iterator = extensions.iterator();
		assertSame(extension1, iterator.next());
		assertSame(extension2, iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testGetExtensionsWithEmptyRegistry() {

		Collection<ISomeExtension> extensions = registry.getExtensions(ISomeExtension.class);
		assertEquals(0, extensions.size());
	}

	@Test(expected = ClassHierarchyValidationException.class)
	public void testGetExtensionsWithIllegalInterface() {

		registry.getExtensions(ISomeDerivedExtension.class);
	}

	// -------------------- test classes and interfaces -------------------- //

	private static interface ISomeExtension extends IEmfModuleExtension {

		void doSomeThing();
	}

	private static interface ISomeDerivedExtension extends ISomeExtension {
		// nothing to add
	}
}
