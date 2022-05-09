package com.softicar.platform.emf.table.configuration;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;

public class EmfTableConfigurationTest extends AbstractDbTest {

	private final EmfTableConfiguration<EmfTestObject, Integer, EmfTestModuleInstance> configuration;

	public EmfTableConfigurationTest() {

		configuration = new EmfTableConfiguration<>(EmfTestObject.TABLE);
	}

	@Test
	public void testGetBusinessKeyWithDefaultValue() {

		assertTrue(configuration.getBusinessKey().isPrimaryKey());
	}

	@Test
	public void testGetBusinessKeyWithNonDefaultValue() {

		configuration.setBusinessKey(EmfTestObject.UK_NAME_DAY);

		assertEquals(EmfTestObject.UK_NAME_DAY, configuration.getBusinessKey());
	}

	@Test(expected = SofticarDeveloperException.class)
	public void testSetBusinessKeyWithKeyThatIsNotUnique() {

		configuration.setBusinessKey(EmfTestObject.KEY_DAY_ACTIVE);
	}

	@Test(expected = SofticarDeveloperException.class)
	public void testSetBusinessKeyWithKeyThatContainsAutoincrementColumn() {

		configuration.setBusinessKey(EmfTestObject.UK_ID_NAME);
	}
}
