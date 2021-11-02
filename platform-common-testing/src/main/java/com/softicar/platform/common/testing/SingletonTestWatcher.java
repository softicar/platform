package com.softicar.platform.common.testing;

import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.common.core.singleton.SingletonSet;
import com.softicar.platform.common.core.singleton.SingletonSetScope;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * A {@link TestWatcher} that puts the whole test execution into a
 * {@link SingletonSetScope}.
 * <p>
 * There is no unit test for this class, because it cannot be tested by a unit
 * test easily. This class is implicitly tested by all unit tests employing it.
 * Without it, the unit tests would be non-deterministic.
 *
 * @author Oliver Richers
 */
class SingletonTestWatcher extends TestWatcher {

	private SingletonSetScope scope = null;

	/**
	 * Creates a {@link SingletonSetScope} with a fresh {@link SingletonSet},
	 * thus, no {@link Singleton} value is inherited from anywhere.
	 */
	public SingletonTestWatcher() {

		this.scope = new SingletonSetScope(new SingletonSet());
	}

	/**
	 * Closes the {@link SingletonSetScope}.
	 */
	@Override
	protected void finished(Description description) {

		scope.close();
	}
}
