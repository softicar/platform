package com.softicar.platform.dom.elements;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.dom.input.DomOption;

/**
 * Represents an option of a {@link AbstractDomValueSelect}.
 *
 * @author Oliver Richers
 */
public class DomValueOption<T> extends DomOption implements IDisplayable {

	private final T value;
	private final IDisplayString displayString;

	/**
	 * Constructs an option object with the specified value and
	 * {@link IDisplayString}.
	 *
	 * @param value
	 *            the value object represented by this option
	 * @param displayString
	 *            the {@link IDisplayString} of this value (never null)
	 */
	public DomValueOption(T value, IDisplayString displayString) {

		this.value = value;
		this.displayString = displayString;

		appendText(displayString);
	}

	/**
	 * Returns the value object represented by this option.
	 *
	 * @return the value object
	 */
	public T getValue() {

		return value;
	}

	@Override
	public IDisplayString toDisplay() {

		return displayString;
	}
}
