package com.softicar.platform.emf.attribute.validator;

import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.IEmfValidator;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;

public class EmfAttributeMandatorynessValidator<R extends IEmfTableRow<R, ?>, V> implements IEmfValidator<R> {

	private final IEmfAttribute<R, V> attribute;

	public EmfAttributeMandatorynessValidator(IEmfAttribute<R, V> attribute) {

		this.attribute = attribute;
	}

	@Override
	public void validate(R tableRow, IEmfValidationResult result) {

		if (attribute.getValue(tableRow) == null && attribute.isMandatory(tableRow)) {
			result.addError(attribute, EmfI18n.THE_ATTRIBUTE_ARG1_IS_MANDATORY.toDisplay(attribute.getTitle()));
		}
	}
}
