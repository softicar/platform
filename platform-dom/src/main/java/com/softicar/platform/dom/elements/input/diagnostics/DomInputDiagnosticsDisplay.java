package com.softicar.platform.dom.elements.input.diagnostics;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;

public class DomInputDiagnosticsDisplay extends DomDiv {

	private DomInputDiagnosticsState state;

	public DomInputDiagnosticsDisplay() {

		this.state = DomInputDiagnosticsState.NONE;

		addCssClass(DomCssClasses.DOM_INPUT_DIAGNOSTICS_DISPLAY);
	}

	/**
	 * Sets the given {@link DomInputDiagnosticsState}.
	 *
	 * @param newState
	 *            the {@link DomInputDiagnosticsState} to set (never
	 *            <i>null</i>)
	 * @return this
	 */
	public DomInputDiagnosticsDisplay setState(DomInputDiagnosticsState newState) {

		DomInputDiagnosticsState oldState = state;

		oldState.getCssClass().ifPresent(this::removeCssClass);
		newState.getCssClass().ifPresent(this::addCssClass);

		this.state = newState;
		return this;
	}

	/**
	 * Resets the {@link DomInputDiagnosticsState} to
	 * {@link DomInputDiagnosticsState#NONE}.
	 *
	 * @return this
	 */
	public DomInputDiagnosticsDisplay clearDiagnosticsState() {

		return setState(DomInputDiagnosticsState.NONE);
	}

	/**
	 * Sets the {@link DomInputDiagnosticsState} to
	 * {@link DomInputDiagnosticsState#SUCCESS}.
	 *
	 * @return this
	 */
	public DomInputDiagnosticsDisplay setSuccessState() {

		return setState(DomInputDiagnosticsState.SUCCESS);
	}

	/**
	 * Sets the {@link DomInputDiagnosticsState} to
	 * {@link DomInputDiagnosticsState#WARNING}.
	 *
	 * @return this
	 */
	public DomInputDiagnosticsDisplay setWarningState() {

		return setState(DomInputDiagnosticsState.WARNING);
	}

	/**
	 * Sets the {@link DomInputDiagnosticsState} to
	 * {@link DomInputDiagnosticsState#ERROR}.
	 *
	 * @return this
	 */
	public DomInputDiagnosticsDisplay setErrorState() {

		return setState(DomInputDiagnosticsState.ERROR);
	}
}
