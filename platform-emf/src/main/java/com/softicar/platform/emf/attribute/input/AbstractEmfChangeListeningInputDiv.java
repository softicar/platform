package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;

public abstract class AbstractEmfChangeListeningInputDiv<T> extends AbstractEmfInputDiv<T> {

	@Override
	public abstract void setChangeCallback(INullaryVoidFunction callback);
}
