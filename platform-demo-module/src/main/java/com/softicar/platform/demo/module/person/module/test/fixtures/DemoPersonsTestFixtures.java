package com.softicar.platform.demo.module.person.module.test.fixtures;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.demo.module.person.module.AGDemoPersonModuleInstance;

public class DemoPersonsTestFixtures implements DemoPersonModuleTestFixtureMethods {

	private final AGDemoPersonModuleInstance moduleInstance;

	public DemoPersonsTestFixtures(AGDemoPersonModuleInstance moduleInstance) {

		this.moduleInstance = moduleInstance;
	}

	public void apply() {

		insertDemoPerson(moduleInstance, "Anton", "Alligator", 123, Day.fromYMD(1970, 1, 1));
		insertDemoPerson(moduleInstance, "Bob", "Bear", 124, null);
		insertDemoPerson(moduleInstance, "Charlie", "Crocodile", 125, Day.fromYMD(1973, 3, 3));
		insertDemoPerson(moduleInstance, "Dixie", "Dog", 126, Day.fromYMD(1978, 8, 8));
	}
}
