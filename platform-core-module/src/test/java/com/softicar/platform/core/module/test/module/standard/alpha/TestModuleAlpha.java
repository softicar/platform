package com.softicar.platform.core.module.test.module.standard.alpha;

import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.core.module.module.AbstractModule;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@TestingOnly
@EmfSourceCodeReferencePointUuid(TestModuleAlpha.UUID)
public class TestModuleAlpha extends AbstractModule<TestModuleAlphaInstance> {

	public static final String UUID = "03c7329a-ced6-4c80-8d57-1a5e2f42634d";

	@Override
	public IModuleInstanceTable<TestModuleAlphaInstance> getModuleInstanceTable() {

		return TestModuleAlphaInstance.TABLE;
	}
}
