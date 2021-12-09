package com.softicar.platform.common.string.csv;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.string.CommonStringI18n;

/**
 * Thrown when a CSV processor detects a CSV syntax violation.
 *
 * @author Alexander Schmidt
 */
public class CsvSyntaxException extends SofticarUserException {

	public CsvSyntaxException(int lineNumber, int characterNumber) {

		super(
			CommonStringI18n.CSV_SYNTAX_ERROR_IN_LINE_ARG1_AT_CHARACTER_ARG2//
				.toDisplay(lineNumber, characterNumber));
	}
}
