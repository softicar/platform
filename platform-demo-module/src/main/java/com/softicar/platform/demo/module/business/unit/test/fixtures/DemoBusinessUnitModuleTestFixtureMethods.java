package com.softicar.platform.demo.module.business.unit.test.fixtures;

import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.business.unit.AGDemoBusinessUnitModuleInstance;
import com.softicar.platform.demo.module.business.unit.partner.AGDemoBusinessPartner;
import com.softicar.platform.demo.module.business.unit.partner.contact.AGDemoBusinessPartnerContact;

public interface DemoBusinessUnitModuleTestFixtureMethods extends CoreModuleTestFixtureMethods {

	default AGDemoBusinessUnitModuleInstance insertDemoBusinessUnitModuleInstance(AGDemoModuleInstance demoModuleInstance) {

		return createStandardModuleInstance(AGDemoBusinessUnitModuleInstance.TABLE)//
			.setDemoModuleInstance(demoModuleInstance)
			.save();
	}

	default AGDemoBusinessPartner insertDemoBusinessPartner(AGDemoBusinessUnitModuleInstance moduleInstance, String name, String vatId) {

		return new AGDemoBusinessPartner()//
			.setModuleInstance(moduleInstance)
			.setName(name)
			.setVatId(vatId)
			.save();
	}

	default AGDemoBusinessPartnerContact insertDemoBusinessPartnerContact(AGDemoBusinessPartner businessPartner, Integer employeeId, String firstName,
			String lastName) {

		return new AGDemoBusinessPartnerContact()//
			.setBusinessPartner(businessPartner)
			.setEmployeeId(employeeId)
			.setFirstName(firstName)
			.setLastName(lastName)
			.save();
	}
}
