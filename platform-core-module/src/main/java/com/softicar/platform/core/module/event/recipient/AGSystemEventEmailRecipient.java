package com.softicar.platform.core.module.event.recipient;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGSystemEventEmailRecipient extends AGSystemEventEmailRecipientGenerated implements IEmfObject<AGSystemEventEmailRecipient> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getRecipient().toDisplayWithoutId();
	}
}
