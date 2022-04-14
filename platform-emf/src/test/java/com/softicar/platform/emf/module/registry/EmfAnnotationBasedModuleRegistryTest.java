package com.softicar.platform.emf.module.registry;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.emf.module.IEmfModule;
import org.junit.Test;

public class EmfAnnotationBasedModuleRegistryTest extends AbstractTest {

	@Test
	public void test() {

		EmfAnnotationBasedModuleRegistry registry = EmfAnnotationBasedModuleRegistry.getInstance();
		for (Class<? extends IEmfModule<?>> moduleClasses: registry.getAllModuleClasses()) {
			IEmfModule<?> module = registry.getModule(moduleClasses);
			assertNotNull(//
				String.format("Failed to load instance of module class %s from the registry.", moduleClasses.getCanonicalName()),
				module);
		}
	}
}
