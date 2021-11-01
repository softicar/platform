package com.softicar.platform.core.module.test.module.standard.alpha;

import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.core.module.module.AbstractStandardModule;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstanceTable;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@TestingOnly
@EmfSourceCodeReferencePointUuid(TestStandardModuleAlpha.UUID)
public class TestStandardModuleAlpha extends AbstractStandardModule<TestStandardModuleAlphaInstance> {

	public static final String UUID = "03c7329a-ced6-4c80-8d57-1a5e2f42634d";

	@Override
	public IStandardModuleInstanceTable<TestStandardModuleAlphaInstance> getModuleInstanceTable() {

		return TestStandardModuleAlphaInstance.TABLE;
	}
}
