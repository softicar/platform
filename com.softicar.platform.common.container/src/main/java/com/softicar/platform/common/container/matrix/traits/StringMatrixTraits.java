package com.softicar.platform.common.container.matrix.traits;

import com.softicar.platform.common.container.matrix.IMatrixTraits;

/**
 * Simple matrix traits for {@link String}.
 * <p>
 * The default value is the empty string and adding concatenates the strings.
 *
 * @author Oliver Richers
 */
public class StringMatrixTraits implements IMatrixTraits<String> {

	@Override
	public String getDefaultValue() {

		return "";
	}

	@Override
	public String plus(String a, String b) {

		return a + b;
	}
}
