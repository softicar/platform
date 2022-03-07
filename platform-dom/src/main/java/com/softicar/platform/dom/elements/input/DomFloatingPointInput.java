package com.softicar.platform.dom.elements.input;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.DomInputException;
import java.util.Objects;
import java.util.function.Function;

/**
 * Common base class for {@link DomFloatInput} and {@link DomDoubleInput}.
 *
 * @author Oliver Richers
 */
public class DomFloatingPointInput<T extends Number> extends AbstractDomNumberInput<T> {

	private final Function<String, T> parser;
	private Function<T, String> formatter;

	public DomFloatingPointInput(Function<String, T> parser) {

		this.parser = parser;
		this.formatter = T::toString;
	}

	public DomFloatingPointInput<T> setFormatter(Function<T, String> formatter) {

		this.formatter = Objects.requireNonNull(formatter);
		return this;
	}

	@Override
	protected String formatValue(T value) {

		return formatter.apply(value);
	}

	@Override
	protected T parseValue(String inputText) {

		try {
			return parser.apply(inputText.replace(",", "."));
		} catch (Exception exception) {
			throw new DomInputException(exception, DomI18n.INVALID_FLOATING_POINT_NUMBER);
		}
	}
}
