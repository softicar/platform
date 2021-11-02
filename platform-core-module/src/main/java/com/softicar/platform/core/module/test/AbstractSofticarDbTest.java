package com.softicar.platform.core.module.test;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import com.softicar.platform.core.module.log.configuration.CurrentLogDbConfiguration;
import com.softicar.platform.db.runtime.test.AbstractDbTest;

public abstract class AbstractSofticarDbTest extends AbstractDbTest {

	protected final TestClock clock;

	public AbstractSofticarDbTest() {

		this.clock = new TestClock();

		CurrentClock.set(clock);
		CurrentLogDbConfiguration.set(new LogDbTestConfiguration());
	}
}
