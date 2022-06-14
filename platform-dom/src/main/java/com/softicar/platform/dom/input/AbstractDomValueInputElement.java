package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.NullaryVoidFunctionList;
import com.softicar.platform.dom.parent.DomParentElement;

public abstract class AbstractDomValueInputElement<V> extends DomParentElement implements IDomValueInput<V> {

	private final NullaryVoidFunctionList changeCallbacks;
	private boolean disabled;

	public AbstractDomValueInputElement() {

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

	/**
	 * Executes all registered callback functions in the other that they were
	 * added.
	 */
	protected final void executeChangeCallbacks() {

		changeCallbacks.apply();
	}

	/**
	 * Override this method to be informed whenever a new callback functions is
	 * added.
	 * <p>
	 * The default implementation does nothing, so there is not need to call
	 * this super implementation.
	 */
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

	/**
	 * Does the actual work to disable or enable this input element.
	 * <p>
	 * Usually, an implementation will call {@link IDomValueInput#setDisabled}
	 * on the contained input elements.
	 *
	 * @param disabled
	 *            <i>true</i> to disable; <i>false</i> to enable
	 */
	protected abstract void doSetDisabled(boolean disabled);
}
