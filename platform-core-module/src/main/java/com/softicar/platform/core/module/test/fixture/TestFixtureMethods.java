package com.softicar.platform.core.module.test.fixture;

/**
 * Provides common methods for test fixtures.
 *
 * @author Oliver Richers
 */
public interface TestFixtureMethods {

	/**
	 * Convenience method to call {@link TestFixtures#use(Class)}.
	 */
	default <T extends ITestFixture> T use(Class<T> textFixtureClass) {

		return TestFixtures.use(textFixtureClass);
	}
}
