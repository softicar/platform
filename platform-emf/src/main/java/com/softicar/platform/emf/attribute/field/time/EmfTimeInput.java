package com.softicar.platform.emf.attribute.field.time;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.elements.time.DomTimeInput;
import com.softicar.platform.emf.attribute.input.IEmfInput;

public class EmfTimeInput extends DomTimeInput implements IEmfInput<Time> {

	@Override
	public IEmfInput<Time> appendLabel(IDisplayString label) {

		//Not supported
		return this;
	}
}
