package com.softicar.platform.core.module.test.instance.registry;

import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.emf.table.IEmfTable;

public interface IStandardModuleTestFixture<I extends IStandardModuleInstance<I>> {

	I getInstance();

	IStandardModuleTestFixture<I> apply();

	IEmfTable<?, ?, ?> getTable();
}
