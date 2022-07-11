package com.softicar.platform.core.module.test.module.beta;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.core.module.module.AbstractModule;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;

@TestingOnly
@SourceCodeReferencePointUuid(TestModuleBeta.UUID)
public class TestModuleBeta extends AbstractModule<TestModuleBetaInstance> {

	public static final String UUID = "1125dcc1-b02b-4788-9747-1ccfd6afc1aa";

	@Override
	public IModuleInstanceTable<TestModuleBetaInstance> getModuleInstanceTable() {

		return TestModuleBetaInstance.TABLE;
	}
}
