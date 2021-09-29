package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGBufferedEmail extends AGBufferedEmailGenerated implements IEmfObject<AGBufferedEmail> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getSubject() + " " + getFrom() + " " + getSender());
	}
}
