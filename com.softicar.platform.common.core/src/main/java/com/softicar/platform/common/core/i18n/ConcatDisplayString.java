package com.softicar.platform.common.core.i18n;

import java.util.Objects;

/**
 * Concatenated two instances of {@link IDisplayString}.
 *
 * @author Oliver Richers
 */
class ConcatDisplayString extends AbstractDisplayString {

	private final IDisplayString first;
	private final IDisplayString second;

	public ConcatDisplayString(IDisplayString first, IDisplayString second) {

		this.first = Objects.requireNonNull(first);
		this.second = Objects.requireNonNull(second);
	}

	@Override
	public String toString() {

		return first.toString() + second.toString();
	}
}
