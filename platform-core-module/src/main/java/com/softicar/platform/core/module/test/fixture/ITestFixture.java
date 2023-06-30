package com.softicar.platform.core.module.test.fixture;

/**
 * Common interface of test fixture classes managed by {@link TestFixtures}.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface ITestFixture {

	void apply();

	/**
	 * Convenience method to call {@link TestFixtures#use(Class)}.
	 */
	default <T extends ITestFixture> T use(Class<T> testFixtureClass) {

		return TestFixtures.use(testFixtureClass);
	}
}
