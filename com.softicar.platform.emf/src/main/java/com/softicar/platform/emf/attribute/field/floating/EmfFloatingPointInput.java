package com.softicar.platform.emf.attribute.field.floating;

import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.input.AbstractEmfInputDiv;
import com.softicar.platform.emf.attribute.input.EmfInputException;

public class EmfFloatingPointInput<V> extends AbstractEmfInputDiv<V> {

	private final IEmfFloatingPointAttributeStrategy<V> strategy;
	private final DomTextInput input;

	public EmfFloatingPointInput(IEmfFloatingPointAttributeStrategy<V> strategy) {

		this.strategy = strategy;
		this.input = new DomTextInput();
		appendChild(input);
	}

	@Override
	public V getValueOrThrow() throws EmfInputException {

		String text = input.getValue();
		if (text != null && !text.trim().isEmpty()) {
			text = getNormalized(text);
			if (strategy.isParseable(text)) {
				return strategy.parseValue(text);
			} else {
				throw new EmfInputException(EmfI18n.INVALID_DECIMAL_NUMBER_ARG1.toDisplay(text));
			}
		} else {
			return null;
		}
	}

	@Override
	public void setValue(V value) {

		input.setValue(value != null? strategy.formatValue(value) : "");
	}

	public String getNormalized(String value) {

		return value.trim().replace(",", ".");
	}
}
