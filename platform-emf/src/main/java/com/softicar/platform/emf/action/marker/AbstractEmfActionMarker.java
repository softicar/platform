package com.softicar.platform.emf.action.marker;

import com.softicar.platform.common.core.interfaces.IStaticObject;

public abstract class AbstractEmfActionMarker implements IStaticObject {

	private final Class<?> actionClass;

	protected AbstractEmfActionMarker(Class<?> actionClass) {

		this.actionClass = actionClass;
	}

	public Class<?> getActionClass() {

		return actionClass;
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof AbstractEmfActionMarker) {
			return actionClass.equals(((AbstractEmfActionMarker) object).actionClass);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return actionClass.hashCode();
	}
}
