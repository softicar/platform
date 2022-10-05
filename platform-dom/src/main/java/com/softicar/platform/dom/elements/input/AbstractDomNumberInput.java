package com.softicar.platform.dom.elements.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.dom.input.DomInputException;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.input.IDomTextualInput;
import java.util.Optional;

/**
 * Abstract base class for various {@link Number} inputs.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDomNumberInput<T extends Number> extends AbstractDomValueInputDiv<T> {

	private final DomTextInput input;

	protected AbstractDomNumberInput() {

		this.input = new DomTextInput();
		this.input.addChangeCallback(this::executeChangeCallbacks);

		appendChild(input);
	}

	@Override
	public void setValue(T value) {

		input.setValue(formatNullableValue(value));
	}

	/**
	 * Parses the value text into a {@link Number}.
	 * <p>
	 * If the value text is empty or blank, an empty {@link Optional} is
	 * returned. Otherwise, the value text is parsed into a {@link Number}. If
	 * parsing failed, an exception is thrown.
	 *
	 * @return the optional value
	 * @throws DomInputException
	 *             if the value text cannot be parsed into a value
	 */
	@Override
	public Optional<T> getValue() throws DomInputException {

		String text = input.getValueTextTrimmed();
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

	public void setPlaceholder(IDisplayString placeholder) {

		input.setPlaceholder(placeholder);
	}

	public String getTextualValue() {

		return input.getValueTextTrimmed();
	}

	@Override
	public Optional<IDomTextualInput> getInputField() {

		return Optional.of(input);
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
	 * Parses the text into a value.
	 * <p>
	 * If parsing fails, an exception will be thrown.
	 *
	 * @param text
	 *            the trimmed text to parse (never <i>null</i> and never empty)
	 * @return the value (never <i>null</i>)
	 */
	protected abstract T parseValue(String text);

	private String formatNullableValue(T value) {

		return value != null? formatValue(value) : "";
	}
}
