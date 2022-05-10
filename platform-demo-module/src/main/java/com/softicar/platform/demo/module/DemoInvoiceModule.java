package com.softicar.platform.demo.module;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.AbstractStandardModule;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstanceTable;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("2a477df7-2004-4f59-b6c6-1c8cfb07baa8")
public class DemoInvoiceModule extends AbstractStandardModule<AGDemoInvoiceModuleInstance> {

	@Override
	public IStandardModuleInstanceTable<AGDemoInvoiceModuleInstance> getModuleInstanceTable() {

		return AGDemoInvoiceModuleInstance.TABLE;
	}

	@Override
	public IDisplayString toDisplay() {

		return DemoI18n.DEMO_INVOICE_MODULE;
	}

	@Override
	public EmfPagePath getDefaultPagePath(AGDemoInvoiceModuleInstance moduleInstance) {

		return CoreModule.getParentPagePath().append(toDisplay());
	}
}
