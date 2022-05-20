package com.softicar.platform.demo.person.module.test.fixtures;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.demo.core.module.AGDemoCoreModuleInstance;
import com.softicar.platform.demo.invoice.module.test.fixture.DemoInvoiceModuleTestFixtureMethods;
import com.softicar.platform.demo.person.module.AGDemoPerson;
import com.softicar.platform.demo.person.module.AGDemoPersonModuleInstance;

public interface DemoPersonModuleTestFixtureMethods extends DemoInvoiceModuleTestFixtureMethods {

	default AGDemoPersonModuleInstance insertDemoPersonModuleInstance(AGDemoCoreModuleInstance demoCoreModuleInstance) {

		return createStandardModuleInstance(AGDemoPersonModuleInstance.TABLE)//
			.setDemoCoreModuleInstance(demoCoreModuleInstance)
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
