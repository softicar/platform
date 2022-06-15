package com.softicar.platform.core.module.test.module.standard.beta;

import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.core.module.module.AbstractModule;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@TestingOnly
@EmfSourceCodeReferencePointUuid(TestModuleBeta.UUID)
public class TestModuleBeta extends AbstractModule<TestModuleBetaInstance> {

	public static final String UUID = "1125dcc1-b02b-4788-9747-1ccfd6afc1aa";

	@Override
	public IModuleInstanceTable<TestModuleBetaInstance> getModuleInstanceTable() {

		return TestModuleBetaInstance.TABLE;
	}
}
