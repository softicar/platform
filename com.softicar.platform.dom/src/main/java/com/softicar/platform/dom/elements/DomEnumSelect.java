package com.softicar.platform.dom.elements;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.input.DomSelect;

/**
 * A special {@link DomSelect} for enum constants.
 *
 * @author Oliver Richers
 */
public class DomEnumSelect<E extends Enum<E>> extends AbstractDomValueSelect<E> {

	/**
	 * Creates a new and empty select.
	 */
	public DomEnumSelect() {

		// nothing to do
	}

	/**
	 * Creates a new select with the specified enum constants.
	 *
	 * @param values
	 *            the enum constants to add to this select
	 */
	@SafeVarargs
	public DomEnumSelect(E...values) {

		addValues(values);
	}

	/**
	 * Creates a new select with the specified enum constants.
	 *
	 * @param values
	 *            the enum constants to add to this select
	 */
	public DomEnumSelect(Iterable<E> values) {

		addValues(values);
	}

	@Override
	protected Integer getValueId(E value) {

		return value.ordinal();
	}

	@Override
	protected IDisplayString getValueDisplayString(E value) {

		return IDisplayString.create(value.toString());
	}
}
