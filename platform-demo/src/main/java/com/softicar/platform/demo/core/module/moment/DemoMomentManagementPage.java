package com.softicar.platform.demo.core.module.moment;

import com.softicar.platform.demo.core.module.AGDemoCoreModuleInstance;
import com.softicar.platform.demo.core.module.DemoCoreModule;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;

@EmfSourceCodeReferencePointUuid("b4d20ed4-7187-48f0-94f0-f59dcab48940")
public class DemoMomentManagementPage extends AbstractEmfManagementPage<AGDemoCoreModuleInstance> {

	@Override
	public Class<? extends IEmfModule<AGDemoCoreModuleInstance>> getModuleClass() {

		return DemoCoreModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGDemoCoreModuleInstance> getTable() {

		return AGDemoMoment.TABLE;
	}
}
