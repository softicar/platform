package com.softicar.platform.demo.module.person.module;

import com.softicar.platform.core.module.module.AbstractStandardModule;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstanceTable;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("907545c0-a852-4c8d-ba98-b483d38a71ec")
public class DemoPersonModule extends AbstractStandardModule<AGDemoPersonModuleInstance> {

	@Override
	public IStandardModuleInstanceTable<AGDemoPersonModuleInstance> getModuleInstanceTable() {

		return AGDemoPersonModuleInstance.TABLE;
	}

	@Override
	public EmfPagePath getDefaultPagePath(AGDemoPersonModuleInstance moduleInstance) {

		return moduleInstance.getDefaultPagePathOfDemoModuleInstance();
	}
}
