package com.softicar.platform.demo.module.business.unit;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.AbstractStandardModule;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstanceTable;
import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("d8a2e5d7-0a4e-4548-ad9c-01d0d502b6bc")
public class DemoBusinessUnitModule extends AbstractStandardModule<AGDemoBusinessUnitModuleInstance> {

	@Override
	public IStandardModuleInstanceTable<AGDemoBusinessUnitModuleInstance> getModuleInstanceTable() {

		return AGDemoBusinessUnitModuleInstance.TABLE;
	}

	@Override
	public IDisplayString toDisplay() {

		return DemoI18n.DEMO_BUSINESS_UNIT_MODULE;
	}

	@Override
	public EmfPagePath getDefaultPagePath(AGDemoBusinessUnitModuleInstance moduleInstance) {

		return CoreModule.getParentPagePath().append(toDisplay());
	}
}
