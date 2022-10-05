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

	// -------------------------------- required -------------------------------- //

	@Override
	public IDomValueInput<V> setRequired(boolean required) {

		setAttribute("required", required? "" : null);
		return this;
	}

	@Override
	public boolean isRequired() {

		return getAttributeValue("required").isPresent();
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
