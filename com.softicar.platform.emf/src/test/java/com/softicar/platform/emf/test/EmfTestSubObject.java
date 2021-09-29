package com.softicar.platform.emf.test;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.sub.object.IEmfSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfTestSubObject extends EmfTestSubObjectGenerated implements IEmfSubObject<EmfTestSubObject, EmfTestObject> {

	public static final IEmfAttribute<EmfTestSubObject, Day> DAY = TABLE.inheritAttribute(EmfTestObject.DAY);
	public static final IEmfAttribute<EmfTestSubObject, Boolean> ACTIVE = TABLE.inheritAttribute(EmfTestObject.ACTIVE);

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString().append(getName());
	}

}
