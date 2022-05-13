package com.softicar.platform.demo.module.person.test.fixtures;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.demo.module.person.AGDemoPersonModuleInstance;

public class DemoPersonTestFixtures implements DemoPersonModuleTestFixtureMethods {

	private final AGDemoPersonModuleInstance moduleInstance;

	public DemoPersonTestFixtures(AGDemoPersonModuleInstance moduleInstance) {

		this.moduleInstance = moduleInstance;
	}

	public void apply() {

		insertDemoPerson(moduleInstance, "Anton", "A.", 123, Day.fromYMD(1970, 1, 1));
		insertDemoPerson(moduleInstance, "Bill", "B.", 124, null);
		insertDemoPerson(moduleInstance, "Ceasar", "C.", 125, Day.fromYMD(1973, 3, 3));
		insertDemoPerson(moduleInstance, "Dixie", "D.", 126, Day.fromYMD(1978, 8, 8));
	}
}
