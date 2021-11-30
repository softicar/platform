package com.softicar.platform.emf.attribute.field;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.input.IDomValueBasedInputNode;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.input.AbstractEmfChangeListeningInputDiv;
import com.softicar.platform.emf.attribute.input.EmfInputException;
import com.softicar.platform.emf.attribute.input.IEmfStringInputNode;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractEmfParsedInput<T> extends AbstractEmfChangeListeningInputDiv<T> {

	protected final IEmfStringInputNode input;
	private final Function<String, T> parserFunction;
	private final IDisplayString typeDescription;

	public AbstractEmfParsedInput(Supplier<? extends IEmfStringInputNode> inputSupplier, Function<String, T> parserFunction, IDisplayString typeDescription) {

		this.input = inputSupplier.get();
		this.parserFunction = parserFunction;
		this.typeDescription = typeDescription;

		appendChild(input);
	}

	@Override
	public T getValueOrThrow() {

		String valueString = input.getValue().trim();

		// we do not decide here if an empty input is allowed
		if (valueString.isEmpty()) {
			return null;
		}

		T value = parserFunction.apply(valueString);
		if (value != null) {
			return value;
		} else {
			throw new EmfInputException(EmfI18n.ARG1_IS_NOT_OF_TYPE_ARG2.toDisplay(valueString, typeDescription));
		}
	}

	@Override
	public void setValue(T value) {

		input.setValue(value != null? value + "" : "");
	}

	@Override
	public IDomElement setEnabled(boolean enabled) {

		input.setEnabled(enabled);
		return this;
	}

	@Override
	public void setChangeCallback(INullaryVoidFunction callback) {

		input.setChangeCallback(callback);
	}

	@Override
	public IDomValueBasedInputNode<T> setPlaceholder(IDisplayString placeholder) {

		input.setPlaceholder(placeholder);
		return this;
	}
}
