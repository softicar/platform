package com.softicar.platform.demo.person.module;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.core.module.module.AbstractModule;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;
import com.softicar.platform.emf.page.EmfPagePath;

@SourceCodeReferencePointUuid("907545c0-a852-4c8d-ba98-b483d38a71ec")
public class DemoPersonModule extends AbstractModule<AGDemoPersonModuleInstance> {

	@Override
	public IModuleInstanceTable<AGDemoPersonModuleInstance> getModuleInstanceTable() {

		return AGDemoPersonModuleInstance.TABLE;
	}

	@Override
	public EmfPagePath getDefaultPagePath(AGDemoPersonModuleInstance moduleInstance) {

		return moduleInstance.getDefaultPagePathOfDemoCoreModule();
	}
}
