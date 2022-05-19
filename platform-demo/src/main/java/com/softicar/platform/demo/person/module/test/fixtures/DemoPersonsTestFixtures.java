package com.softicar.platform.demo.person.module.test.fixtures;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.demo.person.module.AGDemoPersonModuleInstance;

public class DemoPersonsTestFixtures implements DemoPersonModuleTestFixtureMethods {

	private final AGDemoPersonModuleInstance moduleInstance;

	public DemoPersonsTestFixtures(AGDemoPersonModuleInstance moduleInstance) {

		this.moduleInstance = moduleInstance;
	}

	public void apply() {

		insertDemoPerson(moduleInstance, "Arthur", "Dent", 123, Day.fromYMD(1952, 3, 11));
		insertDemoPerson(moduleInstance, "Ford", "Prefect", 124, null);
		insertDemoPerson(moduleInstance, "Zaphod", "Beeblebrox", 125, null);
		insertDemoPerson(moduleInstance, "Tricia", "McMillan", 127, null);
	}
}
