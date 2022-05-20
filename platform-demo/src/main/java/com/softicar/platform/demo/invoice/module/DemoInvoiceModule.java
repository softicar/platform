package com.softicar.platform.demo.invoice.module;

import com.softicar.platform.core.module.module.AbstractStandardModule;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstanceTable;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("5179f251-a1cc-48e7-b859-7132fb040499")
public class DemoInvoiceModule extends AbstractStandardModule<AGDemoInvoiceModuleInstance> {

	@Override
	public IStandardModuleInstanceTable<AGDemoInvoiceModuleInstance> getModuleInstanceTable() {

		return AGDemoInvoiceModuleInstance.TABLE;
	}

	@Override
	public EmfPagePath getDefaultPagePath(AGDemoInvoiceModuleInstance moduleInstance) {

		return moduleInstance.getDefaultPagePathOfDemoCoreModule();
	}
}
