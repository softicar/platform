package com.softicar.platform.core.module.test.fixture;

/**
 * Applier for a test fixture.
 * <p>
 * Usually this interface is implemented by the constructor of a test fixture
 * class.
 *
 * <pre>
 *
 * DatabaseTestSetup setup = new DatabaseTestSetup();
 * MyTestFixture fixture = setup.applyFixture(MyTestFixture::new);
 * </pre>
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface IDatabaseTestFixtureApplier<T> {

	/**
	 * Creates and applies the test fixture.
	 *
	 * @return the newly created test fixture (never null)
	 */
	T apply();
}
