package com.softicar.platform.demo.invoice.module.invoice;

import com.softicar.platform.demo.invoice.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.invoice.module.DemoInvoiceModule;
import com.softicar.platform.emf.management.page.AbstractEmfManagementPage;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.table.IEmfTable;

@EmfSourceCodeReferencePointUuid("23254f8d-92ea-4895-8aef-2837a8b3f368")
public class DemoInvoiceManagementPage extends AbstractEmfManagementPage<AGDemoInvoiceModuleInstance> {

	@Override
	public Class<? extends IEmfModule<AGDemoInvoiceModuleInstance>> getModuleClass() {

		return DemoInvoiceModule.class;
	}

	@Override
	protected IEmfTable<?, ?, AGDemoInvoiceModuleInstance> getTable() {

		return AGDemoInvoice.TABLE;
	}
}
