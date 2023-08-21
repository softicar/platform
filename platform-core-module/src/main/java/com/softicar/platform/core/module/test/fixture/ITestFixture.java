package com.softicar.platform.core.module.test.fixture;

/**
 * Common interface of test fixture classes managed by {@link TestFixtures}.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface ITestFixture extends TestFixtureMethods {

	void apply();
}
