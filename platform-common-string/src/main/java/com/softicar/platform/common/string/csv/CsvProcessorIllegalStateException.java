package com.softicar.platform.common.string.csv;

import com.softicar.platform.common.core.exceptions.SofticarException;

/**
 * Thrown when a CSV processor reached an illegal internal state.
 * <p>
 * <b>Not</b> thrown upon CSV format violations.
 *
 * @author Alexander Schmidt
 */
public class CsvProcessorIllegalStateException extends SofticarException {

	public CsvProcessorIllegalStateException(String message) {

		super(message);
	}
}
