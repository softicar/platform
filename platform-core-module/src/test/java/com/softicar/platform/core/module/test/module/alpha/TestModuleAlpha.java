package com.softicar.platform.core.module.test.module.alpha;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.core.module.module.AbstractModule;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;

@TestingOnly
@SourceCodeReferencePointUuid(TestModuleAlpha.UUID)
public class TestModuleAlpha extends AbstractModule<TestModuleAlphaInstance> {

	public static final String UUID = "03c7329a-ced6-4c80-8d57-1a5e2f42634d";

	@Override
	public IModuleInstanceTable<TestModuleAlphaInstance> getModuleInstanceTable() {

		return TestModuleAlphaInstance.TABLE;
	}
}
