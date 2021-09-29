package com.softicar.platform.emf.action.marker;

import com.softicar.platform.emf.action.IEmfManagementAction;

public class EmfManagementActionMarker extends AbstractEmfActionMarker {

	public EmfManagementActionMarker(IEmfManagementAction<?> action) {

		super(action.getClass());
	}

	public <T extends IEmfManagementAction<?>> EmfManagementActionMarker(Class<T> actionClass) {

		super(actionClass);
	}
}
