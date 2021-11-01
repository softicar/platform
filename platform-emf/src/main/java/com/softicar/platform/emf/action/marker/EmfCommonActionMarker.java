package com.softicar.platform.emf.action.marker;

import com.softicar.platform.emf.action.IEmfCommonAction;

public class EmfCommonActionMarker extends AbstractEmfActionMarker {

	public EmfCommonActionMarker(IEmfCommonAction<?> action) {

		super(action.getClass());
	}

	public <T extends IEmfCommonAction<?>> EmfCommonActionMarker(Class<T> actionClass) {

		super(actionClass);
	}
}
