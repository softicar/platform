package com.softicar.platform.core.module.ajax.event;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.object.IEmfObject;

public class AGAjaxEvent extends AGAjaxEventGenerated implements IEmfObject<AGAjaxEvent> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return CoreI18n.AJAX_EVENT_ARG1_ON_ARG2.toDisplay(getType(), getEventDate());
	}
}
