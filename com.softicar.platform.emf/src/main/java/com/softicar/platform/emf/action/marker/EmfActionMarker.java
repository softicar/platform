package com.softicar.platform.emf.action.marker;

import com.softicar.platform.emf.action.IEmfPrimaryAction;

public class EmfActionMarker extends AbstractEmfActionMarker {

	public EmfActionMarker(IEmfPrimaryAction<?> action) {

		super(action.getClass());
	}

	public EmfActionMarker(Class<? extends IEmfPrimaryAction<?>> actionClass) {

		super(actionClass);
	}
}
