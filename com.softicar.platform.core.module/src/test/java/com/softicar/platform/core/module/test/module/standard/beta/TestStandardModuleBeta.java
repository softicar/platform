package com.softicar.platform.core.module.test.module.standard.beta;

import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.core.module.module.AbstractStandardModule;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstanceTable;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@TestingOnly
@EmfSourceCodeReferencePointUuid(TestStandardModuleBeta.UUID)
public class TestStandardModuleBeta extends AbstractStandardModule<TestStandardModuleBetaInstance> {

	public static final String UUID = "1125dcc1-b02b-4788-9747-1ccfd6afc1aa";

	@Override
	public IStandardModuleInstanceTable<TestStandardModuleBetaInstance> getModuleInstanceTable() {

		return TestStandardModuleBetaInstance.TABLE;
	}
}
