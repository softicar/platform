package com.softicar.platform.demo.module.invoice;

import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.DemoModule;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;

@EmfSourceCodeReferencePointUuid("23254f8d-92ea-4895-8aef-2837a8b3f368")
public class DemoInvoiceManagementPage extends AbstractEmfManagementPage<AGDemoModuleInstance> {

	@Override
	public Class<? extends IEmfModule<AGDemoModuleInstance>> getModuleClass() {

		return DemoModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGDemoModuleInstance> getTable() {

		return AGDemoInvoice.TABLE;
	}
}
