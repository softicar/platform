package com.softicar.platform.core.module.access.module.instance;

import com.softicar.platform.core.module.access.module.AbstractModuleTest;
import com.softicar.platform.core.module.test.module.standard.alpha.TestStandardModuleAlpha;
import com.softicar.platform.core.module.test.module.standard.alpha.TestStandardModuleAlphaInstance;
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
		registry.registerModule(new TestStandardModuleAlpha());
		registry.registerModule(new EmfTestModule());
		CurrentEmfModuleRegistry.set(registry);
	}

	@After
	public void resetModuleRegistry() {

		CurrentEmfModuleRegistry.reset();
	}

	@Test(expected = EmfValidationException.class)
	public void testIsInitializedWithNonStandardModule() {

		UUID moduleUuid = UUID.fromString(EmfTestModule.UUID);
		AGModuleInstance moduleInstance = insertModuleInstance(moduleUuid);

		ModuleInstanceStateChecker checker = createChecker(moduleInstance);

		assertFalse(checker.isInitialized());
	}

	@Test
	public void testIsInitializedWithInitializedStandardModule() {

		UUID moduleUuid = UUID.fromString(TestStandardModuleAlpha.UUID);
		AGModuleInstance moduleInstance = insertModuleInstance(moduleUuid);
		insertStandardModuleInstance(moduleInstance);

		ModuleInstanceStateChecker checker = createChecker(moduleInstance);

		assertTrue(checker.isInitialized());
	}

	@Test
	public void testIsInitializedWithNonInitializedStandardModule() {

		UUID moduleUuid = UUID.fromString(TestStandardModuleAlpha.UUID);
		AGModuleInstance moduleInstance = insertModuleInstance(moduleUuid);

		ModuleInstanceStateChecker checker = createChecker(moduleInstance);

		assertFalse(checker.isInitialized());
	}

	private void insertStandardModuleInstance(AGModuleInstance moduleInstance) {

		new DbTestTableRowInserter<>(TestStandardModuleAlphaInstance.TABLE)//
			.set(TestStandardModuleAlphaInstance.MODULE_INSTANCE, moduleInstance)
			.insert();
	}

	private ModuleInstanceStateChecker createChecker(AGModuleInstance moduleInstance) {

		return new ModuleInstanceStateChecker(moduleInstance);
	}
}
