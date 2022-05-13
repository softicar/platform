package com.softicar.platform.demo.module.person.test.fixtures;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.person.AGDemoPerson;
import com.softicar.platform.demo.module.person.AGDemoPersonModuleInstance;

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
