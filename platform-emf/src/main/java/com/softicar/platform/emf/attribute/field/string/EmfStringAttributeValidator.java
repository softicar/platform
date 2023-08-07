package com.softicar.platform.emf.attribute.field.string;

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
		if (value != null && lengthBits > 0) {
			if (lengthBits == 16 && value.getBytes().length > 65535) {
				result.addError(attribute, EmfI18n.TEXT_IS_TOO_LONG);
			}
			var maxCharacters = getLengthBitsMaximumCharacters(lengthBits);
			if (value.length() > maxCharacters) {
				result.addError(attribute, EmfI18n.MAXIMUM_LENGTH_OF_ARG1_CHARACTERS_EXCEEDED.toDisplay(maxCharacters));
			}
		}
	}

	private boolean isEmpty(String value) {

		return value == null || value.isEmpty();
	}

	private int getLengthBitsMaximumCharacters(int lengthBits) {

		switch (lengthBits) {
		case 8:
			return 255;
		case 24:
			return 16777215;
		case 32:
			return Integer.MAX_VALUE;
		}
		return 0;
	}
}
