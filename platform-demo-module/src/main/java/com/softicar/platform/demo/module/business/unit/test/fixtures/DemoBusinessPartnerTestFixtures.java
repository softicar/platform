package com.softicar.platform.demo.module.business.unit.test.fixtures;

import com.softicar.platform.demo.module.business.unit.AGDemoBusinessUnitModuleInstance;
import com.softicar.platform.demo.module.business.unit.partner.AGDemoBusinessPartner;

public class DemoBusinessPartnerTestFixtures implements DemoBusinessUnitModuleTestFixtureMethods {

	private final AGDemoBusinessUnitModuleInstance moduleInstance;

	public DemoBusinessPartnerTestFixtures(AGDemoBusinessUnitModuleInstance moduleInstance) {

		this.moduleInstance = moduleInstance;
	}

	public void apply() {

		AGDemoBusinessPartner partner1 = insertDemoBusinessPartner(moduleInstance, "Company A", "123");
		insertDemoBusinessPartnerContact(partner1, 1, "Anton", "A");
		insertDemoBusinessPartnerContact(partner1, 2, "Bill", "B");
		insertDemoBusinessPartnerContact(partner1, 3, "Ceasar", "C");

		AGDemoBusinessPartner partner2 = insertDemoBusinessPartner(moduleInstance, "Company B", "456");
		insertDemoBusinessPartnerContact(partner2, 4, "Dolly", "D");
		insertDemoBusinessPartnerContact(partner2, 5, "Edward", "E");
	}
}
