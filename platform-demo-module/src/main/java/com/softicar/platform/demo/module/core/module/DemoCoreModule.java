package com.softicar.platform.demo.module.core.module;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.AbstractStandardModule;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstanceTable;
import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("2a477df7-2004-4f59-b6c6-1c8cfb07baa8")
public class DemoCoreModule extends AbstractStandardModule<AGDemoCoreModuleInstance> {

	@Override
	public IStandardModuleInstanceTable<AGDemoCoreModuleInstance> getModuleInstanceTable() {

		return AGDemoCoreModuleInstance.TABLE;
	}

	@Override
	public IDisplayString toDisplay() {

		return DemoI18n.DEMO;
	}

	@Override
	public EmfPagePath getDefaultPagePath(AGDemoCoreModuleInstance moduleInstance) {

		return getDefaultPagePath();
	}

	public static EmfPagePath getDefaultPagePath() {

		return CoreModule.getParentPagePath().append(DemoI18n.DEMO);
	}
}
