package com.softicar.platform.core.module.test.fixture;

import com.softicar.platform.common.container.map.instance.ClassInstanceMap;
import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.common.core.utils.ReflectionUtils;
import com.softicar.platform.db.core.transaction.DbOptionalLazyTransaction;
import com.softicar.platform.emf.table.listener.EmfTableListenerSettings;

/**
 * Manages instances of {@link ITestFixture}.
 *
 * @author Oliver Richers
 */
public class TestFixtures {

	private static final Singleton<TestFixtures> INSTANCE = new Singleton<>(TestFixtures::new).setInheritByIdentity();
	private final ClassInstanceMap<ITestFixture> testFixtures;

	private TestFixtures() {

		this.testFixtures = new ClassInstanceMap<>();
	}

	/**
	 * Returns the instance of the given {@link ITestFixture} class.
	 * <p>
	 * If the instance was not created yet, it will be instantiated and
	 * {@link ITestFixture#apply()} will be called. It is guaranteed that for
	 * every {@link ITestFixture} class only one instance is created and
	 * applied.
	 *
	 * @param textFixtureClass
	 *            the class of the {@link ITestFixture} to return (never
	 *            <i>null</i>)
	 * @return the {@link ITestFixture} instance (never <i>null</i>)
	 */
	public static <T extends ITestFixture> T use(Class<T> textFixtureClass) {

		return INSTANCE.get().getOrApplyTestFixture(textFixtureClass);
	}

	private <T extends ITestFixture> T getOrApplyTestFixture(Class<T> textFixtureClass) {

		return testFixtures.getOrPutInstance(textFixtureClass, () -> createAndApplyTestFixture(textFixtureClass));
	}

	private <T extends ITestFixture> T createAndApplyTestFixture(Class<T> textFixtureClass) {

		return applyFixture(ReflectionUtils.newInstance(textFixtureClass));
	}

	private static <T extends ITestFixture> T applyFixture(T testFixture) {

		try (var transaction = new DbOptionalLazyTransaction()) {
			EmfTableListenerSettings.setLoggingEnabled(false);
			testFixture.apply();
			transaction.commit();
		}
		return testFixture;
	}
}
