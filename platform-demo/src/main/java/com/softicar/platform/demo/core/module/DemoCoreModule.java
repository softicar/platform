package com.softicar.platform.demo.core.module;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.module.AbstractModule;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;
import com.softicar.platform.demo.DemoI18n;
import com.softicar.platform.emf.page.EmfPagePath;

@SourceCodeReferencePointUuid("2a477df7-2004-4f59-b6c6-1c8cfb07baa8")
public class DemoCoreModule extends AbstractModule<AGDemoCoreModuleInstance> {

	@Override
	public IModuleInstanceTable<AGDemoCoreModuleInstance> getModuleInstanceTable() {

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
