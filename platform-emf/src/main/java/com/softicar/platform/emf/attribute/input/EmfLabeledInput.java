package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomSpan;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.dom.input.IDomInput;
import com.softicar.platform.dom.input.IDomValueInput;
import com.softicar.platform.emf.EmfCssClasses;
import java.util.Optional;

public class EmfLabeledInput<V> extends DomDiv implements IEmfInput<V> {

	private final IEmfInput<V> emfInput;

	public EmfLabeledInput(IEmfInput<V> input, IDisplayString label) {

		setCssClass(EmfCssClasses.EMF_LABELED_INPUT);
		this.emfInput = appendChild(input);
		setLabel(label);
	}

	public IEmfInput<V> setLabel(IDisplayString label) {

		if (emfInput instanceof AbstractDomValueInputDiv) {
			var input = ((AbstractDomValueInputDiv<?>) emfInput).getInputField();
			input.ifPresent(it -> appendLabel(createLabel(label), it));
		} else {
			appendLabel(createLabel(label), emfInput);
		}
		return this;
	}

	private DomSpan createLabel(IDisplayString label) {

		var labelSpan = new DomSpan();
		labelSpan.appendText(label);
		labelSpan.addCssClass(EmfCssClasses.EMF_INPUT_LABEL);
		return labelSpan;
	}

	private void appendLabel(DomSpan label, IDomValueInput<?> input) {

		input.setRequired(true);
		input.getParent().appendChild(label);
	}

	@Override
	public void setValue(V value) {

		emfInput.setValue(value);
	}

	@Override
	public void setValueAndHandleChangeCallback(V value) {

		emfInput.setValueAndHandleChangeCallback(value);
	}

	@Override
	public Optional<V> getValue() {

		return emfInput.getValue();
	}

	@Override
	public void addChangeCallback(INullaryVoidFunction callback) {

		emfInput.addChangeCallback(callback);
	}

	@Override
	public IDomInput setDisabled(boolean disabled) {

		return emfInput.setDisabled(disabled);
	}

	@Override
	public boolean isDisabled() {

		return emfInput.isDisabled();
	}

	@Override
	public IDomInput setEnabled(boolean enabled) {

		return emfInput.setEnabled(enabled);
	}

	@Override
	public boolean isEnabled() {

		return emfInput.isEnabled();
	}

	@Override
	public IDomValueInput<V> setRequired(boolean required) {

		return emfInput.setRequired(required);
	}

	@Override
	public boolean isRequired() {

		return emfInput.isRequired();
	}

}
