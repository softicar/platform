package com.softicar.platform.core.module.test.instance.registry;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixture;
import com.softicar.platform.emf.table.IEmfTable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class TestFixtureRegistry {

	private final Map<IEmfTable<?, ?, ?>, IModuleTestFixture<?>> testFixtureMap;
	//FIXME merge this into testFixtureMap once CoreModule is a standard module
	private final CoreModuleTestFixture coreModuleTestFixture;

	public TestFixtureRegistry(CoreModuleTestFixture coreModuleTestFixture) {

		this.coreModuleTestFixture = coreModuleTestFixture;
		this.testFixtureMap = new HashMap<>();
	}

	public <T extends IModuleTestFixture<?>> T registerIfMissing(Function<TestFixtureRegistry, T> function) {

		T testFixture = function.apply(this);
		Objects.requireNonNull(testFixture);
		if (!isAlreadyRegistered(testFixture)) {
			testFixture.apply();
			testFixtureMap.put(testFixture.getTable(), testFixture);
			return testFixture;
		} else {
			Log.finfo("Skipped '%s' because it already is registered.", testFixture.getClass().getSimpleName());
			return CastUtils.cast(testFixtureMap.get(testFixture.getTable()));
		}
	}

	public <I extends IModuleInstance<I>, T extends IModuleTestFixture<I>> T getTestFixture(IEmfTable<I, ?, ?> table) {

		return CastUtils.cast(testFixtureMap.get(table));
	}

	public <I extends IModuleInstance<I>> I getModuleInstance(IEmfTable<I, ?, ?> table) {

		return CastUtils.cast(getTestFixture(table).getInstance());
	}

	public CoreModuleTestFixture getCoreModuleTestFixture() {

		return coreModuleTestFixture;
	}

	private <T extends IModuleTestFixture<?>> boolean isAlreadyRegistered(T testFixture) {

		return testFixtureMap.containsKey(testFixture.getTable());
	}
}
