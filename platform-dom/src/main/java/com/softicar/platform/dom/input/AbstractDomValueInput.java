package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.NullaryVoidFunctionList;
import com.softicar.platform.dom.elements.DomDiv;

public abstract class AbstractDomValueInput<V> extends DomDiv implements IDomValueInput<V> {

	private final NullaryVoidFunctionList changeCallbacks;
	private boolean disabled;

	public AbstractDomValueInput() {

		this.changeCallbacks = new NullaryVoidFunctionList();
		this.disabled = false;
	}

	// ------------------------------ change callback ------------------------------ //

	@Override
	public final void addChangeCallback(INullaryVoidFunction callback) {

		changeCallbacks.add(callback);
		onChangeCallbackAdded();
	}

	@Override
	public final void setValueAndHandleChangeCallback(V value) {

		setValue(value);
		executeChangeCallbacks();
	}

	protected final void executeChangeCallbacks() {

		changeCallbacks.apply();
	}

	protected void onChangeCallbackAdded() {

		// nothing to do by default
	}

	// ------------------------------ disable ------------------------------ //

	@Override
	public final IDomInput setDisabled(boolean disabled) {

		if (this.disabled != disabled) {
			doSetDisabled(disabled);
			this.disabled = disabled;
		}
		return this;
	}

	@Override
	public final boolean isDisabled() {

		return disabled;
	}

	@Override
	public final IDomInput setEnabled(boolean enabled) {

		return setDisabled(!enabled);
	}

	@Override
	public final boolean isEnabled() {

		return !isDisabled();
	}

	protected abstract void doSetDisabled(boolean disabled);
}
