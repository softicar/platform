package com.softicar.platform.emf.form.derived;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class EmfTestObjectWithDerivedValue extends EmfTestObjectWithDerivedValueGenerated implements IEmfObject<EmfTestObjectWithDerivedValue> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create("");
	}
}
