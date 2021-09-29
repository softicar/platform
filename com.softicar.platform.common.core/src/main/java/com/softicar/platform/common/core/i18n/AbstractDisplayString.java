package com.softicar.platform.common.core.i18n;

import java.util.Objects;

/**
 * FIXME i70184 should not exist
 */
abstract class AbstractDisplayString implements IDisplayString {

	@Override
	public final boolean equals(Object other) {

		if (other instanceof IDisplayString) {
			return compareTo((IDisplayString) other) == 0;
		} else {
			return false;
		}
	}

	@Override
	public final int hashCode() {

		return Objects.hash(toString());
	}
}
