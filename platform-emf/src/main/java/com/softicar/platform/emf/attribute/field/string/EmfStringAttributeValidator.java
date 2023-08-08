package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.IEmfValidator;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;

public class EmfStringAttributeValidator<R extends IEmfTableRow<R, ?>> implements IEmfValidator<R> {

	private final EmfStringAttribute<R> attribute;

	public EmfStringAttributeValidator(EmfStringAttribute<R> attribute) {

		this.attribute = attribute;
	}

	@Override
	public void validate(R tableRow, IEmfValidationResult result) {

		String value = attribute.getValue(tableRow);

		if (isEmpty(value) && attribute.isMandatory(tableRow)) {
			result.addError(attribute, EmfI18n.THE_ATTRIBUTE_ARG1_IS_MANDATORY.toDisplay(attribute.getTitle()));
		}

		int maximumLength = attribute.getMaximumLength();
		if (value != null && maximumLength > 0 && value.length() > maximumLength) {
			result.addError(attribute, EmfI18n.MAXIMUM_LENGTH_OF_ARG1_CHARACTERS_EXCEEDED.toDisplay(maximumLength));
		}

		int lengthBits = attribute.getLengthBits();
		if (value != null && lengthBits > 0 && value.getBytes(Charsets.UTF8).length > getMaximumBytes(lengthBits)) {
			result.addError(attribute, EmfI18n.TEXT_IS_TOO_LONG);
		}
	}

	private boolean isEmpty(String value) {

		return value == null || value.isEmpty();
	}

	private int getMaximumBytes(int lengthBits) {

		switch (lengthBits) {
		case 8:
			return 255;
		case 16:
			return 65535;
		case 24:
			return 16777215;
		case 32:
			return Integer.MAX_VALUE;
		}
		return 0;
	}
}
