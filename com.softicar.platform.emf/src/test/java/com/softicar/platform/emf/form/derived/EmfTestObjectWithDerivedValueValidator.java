package com.softicar.platform.emf.form.derived;

import com.softicar.platform.emf.validation.AbstractEmfValidator;

public class EmfTestObjectWithDerivedValueValidator extends AbstractEmfValidator<EmfTestObjectWithDerivedValue> {

	@Override
	public void validate() {

		assertNotNull(EmfTestObjectWithDerivedValue.VALUE);
		assertNotNull(EmfTestObjectWithDerivedValue.DERIVED_VALUE);
	}
}
