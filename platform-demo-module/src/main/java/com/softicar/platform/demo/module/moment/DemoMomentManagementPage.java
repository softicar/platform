package com.softicar.platform.demo.module.moment;

import com.softicar.platform.demo.module.core.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.core.module.DemoModule;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;

@EmfSourceCodeReferencePointUuid("b4d20ed4-7187-48f0-94f0-f59dcab48940")
public class DemoMomentManagementPage extends AbstractEmfManagementPage<AGDemoModuleInstance> {

	@Override
	public Class<? extends IEmfModule<AGDemoModuleInstance>> getModuleClass() {

		return DemoModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGDemoModuleInstance> getTable() {

		return AGDemoMoment.TABLE;
	}
}
