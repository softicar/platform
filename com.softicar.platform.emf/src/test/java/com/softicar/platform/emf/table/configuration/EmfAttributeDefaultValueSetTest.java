package com.softicar.platform.emf.table.configuration;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;
import org.junit.Test;

public class EmfAttributeDefaultValueSetTest extends AbstractDbTest {

	private static final String FOO = "FOO";
	private static final Day SOME_DAY = Day.fromYMD(2018, 4, 1);

	@Test
	public void test() {

		EmfTestModuleInstance moduleInstance = new EmfTestModuleInstance();

		EmfAttributeDefaultValueSet<EmfTestSubObject, EmfTestModuleInstance> defaultValueSet = new EmfAttributeDefaultValueSet<>(EmfTestSubObject.TABLE);
		defaultValueSet.setValue(EmfTestSubObject.NAME, FOO);
		defaultValueSet.setValue(EmfTestSubObject.DAY, SOME_DAY);

		EmfTestSubObject entity = EmfTestSubObject.TABLE.createEntity(moduleInstance);
		defaultValueSet.applyTo(entity, moduleInstance);

		assertEquals(FOO, entity.getName());
		assertEquals(SOME_DAY, entity.getSimpleEntity().getDay());
	}
}
