package com.softicar.platform.common.string.csv;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.CommonStringI18n;

/**
 * Thrown when a CSV processor detects a CSV format violation.
 *
 * @author Alexander Schmidt
 */
public class CsvFormatException extends SofticarUserException {

	public CsvFormatException(IDisplayString message, int index) {

		super(
			CommonStringI18n.INVALID_CSV_FORMAT_AFTER_CHARACTER_ARG1//
				.toDisplay(index)
				.concatColon()
				.concatSentence(message));
	}
}
