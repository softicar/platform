package com.softicar.platform.demo.module.moment;

import com.softicar.platform.demo.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.module.DemoInvoiceModule;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;

@EmfSourceCodeReferencePointUuid("b4d20ed4-7187-48f0-94f0-f59dcab48940")
public class DemoMomentManagementPage extends AbstractEmfManagementPage<AGDemoInvoiceModuleInstance> {

	@Override
	public Class<? extends IEmfModule<AGDemoInvoiceModuleInstance>> getModuleClass() {

		return DemoInvoiceModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGDemoInvoiceModuleInstance> getTable() {

		return AGDemoMoment.TABLE;
	}
}
