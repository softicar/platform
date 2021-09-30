package com.softicar.platform.emf.attribute.field.indirect.entity.foreign;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;

public class EmfForeignIndirectEntityAttributeCastException extends SofticarException {

	public EmfForeignIndirectEntityAttributeCastException(EmfFieldAttribute<?, ?> attribute) {

		super(//
			"The given attribute '%s' is not of type '%s'.",
			attribute.getTitle(),
			EmfForeignIndirectEntityAttribute.class.getSimpleName());
	}
}
