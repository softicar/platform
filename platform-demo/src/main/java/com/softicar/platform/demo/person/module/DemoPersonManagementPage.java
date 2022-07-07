package com.softicar.platform.demo.person.module;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.table.IEmfTable;

@SourceCodeReferencePointUuid("5bc46ac5-cc79-4bdd-a1d1-bb0cb0f4d261")
public class DemoPersonManagementPage extends AbstractEmfManagementPage<AGDemoPersonModuleInstance> {

	@Override
	public Class<? extends IEmfModule<AGDemoPersonModuleInstance>> getModuleClass() {

		return DemoPersonModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGDemoPersonModuleInstance> getTable() {

		return AGDemoPerson.TABLE;
	}
}
