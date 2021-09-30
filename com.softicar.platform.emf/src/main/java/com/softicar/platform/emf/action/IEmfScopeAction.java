package com.softicar.platform.emf.action;

public interface IEmfScopeAction<S> extends IEmfAction<S> {

	void handleClick(S scope);
}
