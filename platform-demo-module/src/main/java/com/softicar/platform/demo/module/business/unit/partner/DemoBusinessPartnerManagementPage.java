package com.softicar.platform.demo.module.business.unit.partner;

import com.softicar.platform.demo.module.business.unit.AGDemoBusinessUnitModuleInstance;
import com.softicar.platform.demo.module.business.unit.DemoBusinessUnitModule;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;

@EmfSourceCodeReferencePointUuid("c9adcce3-b190-4277-8045-fd7c3aa06102")
public class DemoBusinessPartnerManagementPage extends AbstractEmfManagementPage<AGDemoBusinessUnitModuleInstance> {

	@Override
	public Class<? extends IEmfModule<AGDemoBusinessUnitModuleInstance>> getModuleClass() {

		return DemoBusinessUnitModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGDemoBusinessUnitModuleInstance> getTable() {

		return AGDemoBusinessPartner.TABLE;
	}
}
