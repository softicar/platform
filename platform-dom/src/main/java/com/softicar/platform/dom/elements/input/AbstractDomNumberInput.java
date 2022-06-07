package com.softicar.platform.dom.elements.input;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.AbstractDomValueInput;
import com.softicar.platform.dom.input.DomInputException;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.Optional;

/**
 * Abstract base class for various {@link Number} inputs.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDomNumberInput<T extends Number> extends AbstractDomValueInput<T> {

	private final TextInput input;

	protected AbstractDomNumberInput() {

		this.input = new TextInput();

		appendChild(input);
	}

	@Override
	public void setValue(T value) {

		input.setInputText(formatNullableValue(value));
	}

	/**
	 * Parses the input text into a proper value.
	 * <p>
	 * If the input text is empty or blank, an empty {@link Optional} is
	 * returned. Otherwise, the input text is parsed into a value. If parsing
	 * failed, an exception is thrown.
	 *
	 * @return the optional value
	 * @throws DomInputException
	 *             if the input text cannot be parsed into a value
	 */
	@Override
	public Optional<T> getValue() throws DomInputException {

		String text = input.getInputTextTrimmed();
		if (!text.isBlank()) {
			return Optional.of(parseValue(text));
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Same as {@link #getValue()} but never throws an {@link Exception}.
	 * <p>
	 * Instead of throwing an exception, {@link Optional#empty()} is returned.
	 *
	 * @return the entered {@link Day}
	 */
	@Override
	public Optional<T> getValueNoThrow() {

		try {
			return getValue();
		} catch (Exception exception) {
			DevNull.swallow(exception);
			return Optional.empty();
		}
	}

	public String getInputText() {

		return input.getInputText();
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		input.setDisabled(disabled);
	}

	/**
	 * Converts the value into a textual representation.
	 *
	 * @param value
	 *            the value to convert (never <i>null</i>)
	 * @return the textual representation (never <i>null</i>)
	 */
	protected abstract String formatValue(T value);

	/**
	 * Parses the input text into a value.
	 * <p>
	 * If parsing fails, a exception will be thrown.
	 *
	 * @param inputText
	 *            the trimmed input text to parse (never <i>null</i> and never
	 *            empty)
	 * @return the value (never <i>null</i>)
	 */
	protected abstract T parseValue(String inputText);

	private String formatNullableValue(T value) {

		return value != null? formatValue(value) : "";
	}

	private class TextInput extends DomTextInput implements IDomChangeEventHandler {

		@Override
		public void handleChange(IDomEvent event) {

			executeChangeCallbacks();
		}
	}
}
