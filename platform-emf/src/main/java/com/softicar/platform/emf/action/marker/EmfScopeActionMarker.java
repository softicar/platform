package com.softicar.platform.emf.action.marker;

import com.softicar.platform.emf.action.IEmfScopeAction;

public class EmfScopeActionMarker extends AbstractEmfActionMarker {

	public EmfScopeActionMarker(IEmfScopeAction<?> action) {

		super(action.getClass());
	}

	public <T extends IEmfScopeAction<?>> EmfScopeActionMarker(Class<T> actionClass) {

		super(actionClass);
	}
}
