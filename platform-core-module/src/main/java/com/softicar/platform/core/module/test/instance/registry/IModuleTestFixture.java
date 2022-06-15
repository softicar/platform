package com.softicar.platform.core.module.test.instance.registry;

import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.emf.table.IEmfTable;

public interface IModuleTestFixture<I extends IModuleInstance<I>> {

	I getInstance();

	IModuleTestFixture<I> apply();

	IEmfTable<?, ?, ?> getTable();
}
