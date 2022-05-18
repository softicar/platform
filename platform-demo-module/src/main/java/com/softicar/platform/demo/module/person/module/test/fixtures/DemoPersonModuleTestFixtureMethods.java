package com.softicar.platform.demo.module.person.module.test.fixtures;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.demo.module.core.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.person.module.AGDemoPerson;
import com.softicar.platform.demo.module.person.module.AGDemoPersonModuleInstance;

public interface DemoPersonModuleTestFixtureMethods extends CoreModuleTestFixtureMethods {

	default AGDemoPersonModuleInstance insertDemoPersonModuleInstance(AGDemoModuleInstance demoModuleInstance) {

		return createStandardModuleInstance(AGDemoPersonModuleInstance.TABLE)//
			.setDemoModuleInstance(demoModuleInstance)
			.save();
	}

	default AGDemoPerson insertDemoPerson(AGDemoPersonModuleInstance moduleInstance, String firstName, String lastName, int identityCardNumber, Day birthDate) {

		return new AGDemoPerson()//
			.setModuleInstance(moduleInstance)
			.setFirstName(firstName)
			.setLastName(lastName)
			.setIdentityCardNumber(identityCardNumber)
			.setBirthDate(birthDate)
			.save();
	}
}
