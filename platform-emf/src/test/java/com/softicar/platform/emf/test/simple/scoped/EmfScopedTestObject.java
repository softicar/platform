package com.softicar.platform.emf.test.simple.scoped;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class EmfScopedTestObject extends EmfScopedTestObjectGenerated implements IEmfObject<EmfScopedTestObject> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString().append(getName());
	}
}
