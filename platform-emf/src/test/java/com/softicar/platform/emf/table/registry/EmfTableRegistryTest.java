package com.softicar.platform.emf.table.registry;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.test.module.alpha.TestModuleAlpha;
import com.softicar.platform.emf.test.module.alpha.TestModuleAlphaInstance;
import com.softicar.platform.emf.test.module.alpha.entity.TestModuleAlphaEntity;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.trait.EmfTestTrait;
import com.softicar.platform.emf.trait.table.IEmfTraitTable;
import java.util.Collection;
import org.junit.Test;

public class EmfTableRegistryTest extends AbstractTest {

	@Test
	public void testGetTraitTables() {

		Collection<IEmfTraitTable<?, EmfTestObject>> traitTables = EmfTableRegistry.getInstance().getTraitTables(EmfTestObject.TABLE);

		IEmfTraitTable<?, EmfTestObject> traitTable = Asserts.assertOne(traitTables);
		assertSame(EmfTestTrait.TABLE, traitTable);
	}

	@Test
	public void testGetTablesWithModule() {

		TestModuleAlpha moduleAlpha = CurrentEmfModuleRegistry.get().getModule(TestModuleAlpha.class);
		Collection<IEmfTable<?, ?, ?>> tables = EmfTableRegistry.getInstance().getTables(moduleAlpha);

		Asserts.assertCount(2, tables);
		tables.contains(TestModuleAlphaEntity.TABLE);
		tables.contains(TestModuleAlphaInstance.TABLE);
	}
}
