package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class EmfTestBusinessUnitModuleInstance extends EmfTestBusinessUnitModuleInstanceGenerated implements IEmfObject<EmfTestBusinessUnitModuleInstance> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}
}
