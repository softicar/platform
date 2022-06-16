package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.core.module.access.module.AbstractModuleTest;
import com.softicar.platform.core.module.test.module.alpha.TestModuleAlpha;
import com.softicar.platform.core.module.test.module.alpha.TestModuleAlphaInstance;
import com.softicar.platform.db.runtime.test.utils.DbTestTableRowInserter;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.test.module.EmfTestModule;
import com.softicar.platform.emf.test.module.EmfTestModuleRegistry;
import com.softicar.platform.emf.validation.EmfValidationException;
import java.util.UUID;
import org.junit.After;
import org.junit.Test;

public class ModuleInstanceStateCheckerTest extends AbstractModuleTest {

	public ModuleInstanceStateCheckerTest() {

		EmfTestModuleRegistry registry = new EmfTestModuleRegistry();
		registry.registerModule(new TestModuleAlpha());
		registry.registerModule(new EmfTestModule());
		CurrentEmfModuleRegistry.set(registry);
	}

	@After
	public void resetModuleRegistry() {

		CurrentEmfModuleRegistry.reset();
	}

	@Test(expected = EmfValidationException.class)
	public void testIsInitializedWithMissingImplementationOfModuleInterface() {

		UUID moduleUuid = UUID.fromString(EmfTestModule.UUID);
		AGModuleInstance moduleInstance = insertModuleInstance(moduleUuid);

		ModuleInstanceStateChecker checker = createChecker(moduleInstance);

		assertFalse(checker.isInitialized());
	}

	@Test
	public void testIsInitializedWithInitializedModule() {

		UUID moduleUuid = UUID.fromString(TestModuleAlpha.UUID);
		AGModuleInstance moduleInstance = insertModuleInstance(moduleUuid);
		insertModuleInstance(moduleInstance);

		ModuleInstanceStateChecker checker = createChecker(moduleInstance);

		assertTrue(checker.isInitialized());
	}

	@Test
	public void testIsInitializedWithNonInitializedModule() {

		UUID moduleUuid = UUID.fromString(TestModuleAlpha.UUID);
		AGModuleInstance moduleInstance = insertModuleInstance(moduleUuid);

		ModuleInstanceStateChecker checker = createChecker(moduleInstance);

		assertFalse(checker.isInitialized());
	}

	private void insertModuleInstance(AGModuleInstance moduleInstance) {

		new DbTestTableRowInserter<>(TestModuleAlphaInstance.TABLE)//
			.set(TestModuleAlphaInstance.MODULE_INSTANCE, moduleInstance)
			.insert();
	}

	private ModuleInstanceStateChecker createChecker(AGModuleInstance moduleInstance) {

		return new ModuleInstanceStateChecker(moduleInstance);
	}
}
