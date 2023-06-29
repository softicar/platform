package com.softicar.platform.core.module.test.instance.registry;

import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.test.fixture.ITestFixture;
import com.softicar.platform.core.module.test.fixture.TestFixtures;
import com.softicar.platform.emf.table.IEmfTable;

public interface IModuleTestFixture<I extends IModuleInstance<I>> {

	I getInstance();

	IModuleTestFixture<I> apply();

	IEmfTable<?, ?, ?> getTable();

	/**
	 * Convenience method to call {@link TestFixtures#use(Class)}.
	 */
	default <T extends ITestFixture> T use(Class<T> testFixtureClass) {

		return TestFixtures.use(testFixtureClass);
	}
}
