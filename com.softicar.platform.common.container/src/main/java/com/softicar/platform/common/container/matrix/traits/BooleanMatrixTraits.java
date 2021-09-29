package com.softicar.platform.common.container.matrix.traits;

import com.softicar.platform.common.container.matrix.IMatrixTraits;

/**
 * Default {@link IMatrixTraits} for {@link Boolean}.
 * <p>
 * The default value is <i>false</i> and adding two boolean values uses the
 * boolean <i>OR</i> operation.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class BooleanMatrixTraits implements IMatrixTraits<Boolean> {

	@Override
	public Boolean getDefaultValue() {

		return false;
	}

	@Override
	public Boolean plus(Boolean q1, Boolean q2) {

		return q1 || q2;
	}
}
