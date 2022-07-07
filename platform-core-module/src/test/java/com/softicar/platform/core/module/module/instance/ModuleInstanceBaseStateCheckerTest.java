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

public class ModuleInstanceBaseStateCheckerTest extends AbstractModuleTest {

	public ModuleInstanceBaseStateCheckerTest() {

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
		AGModuleInstanceBase moduleInstanceBase = insertModuleInstanceBase(moduleUuid);

		ModuleInstanceBaseStateChecker checker = createChecker(moduleInstanceBase);

		assertFalse(checker.isInitialized());
	}

	@Test
	public void testIsInitializedWithInitializedModule() {

		UUID moduleUuid = UUID.fromString(TestModuleAlpha.UUID);
		AGModuleInstanceBase moduleInstanceBase = insertModuleInstanceBase(moduleUuid);
		insertModuleInstance(moduleInstanceBase);

		ModuleInstanceBaseStateChecker checker = createChecker(moduleInstanceBase);

		assertTrue(checker.isInitialized());
	}

	@Test
	public void testIsInitializedWithNonInitializedModule() {

		UUID moduleUuid = UUID.fromString(TestModuleAlpha.UUID);
		AGModuleInstanceBase moduleInstanceBase = insertModuleInstanceBase(moduleUuid);

		ModuleInstanceBaseStateChecker checker = createChecker(moduleInstanceBase);

		assertFalse(checker.isInitialized());
	}

	private void insertModuleInstance(AGModuleInstanceBase moduleInstanceBase) {

		new DbTestTableRowInserter<>(TestModuleAlphaInstance.TABLE)//
			.set(TestModuleAlphaInstance.BASE, moduleInstanceBase)
			.insert();
	}

	private ModuleInstanceBaseStateChecker createChecker(AGModuleInstanceBase moduleInstance) {

		return new ModuleInstanceBaseStateChecker(moduleInstance);
	}
}
