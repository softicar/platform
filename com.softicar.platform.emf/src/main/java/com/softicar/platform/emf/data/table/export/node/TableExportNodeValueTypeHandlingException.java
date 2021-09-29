package com.softicar.platform.emf.data.table.export.node;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;

public class TableExportNodeValueTypeHandlingException extends SofticarDeveloperException {

	public TableExportNodeValueTypeHandlingException(TableExportNodeValueType valueType) {

		this(valueType, null);
	}

	public TableExportNodeValueTypeHandlingException(TableExportNodeValueType valueType, String reason) {

		super(buildMessage(valueType, reason));
	}

	private static String buildMessage(TableExportNodeValueType valueType, String reason) {

		String output = "Don't know how to handle the given %s '%s'";

		if (reason != null) {
			output += ": " + reason;
		} else {
			output += ".";
		}

		return String.format(output, TableExportNodeValueType.class.getSimpleName(), valueType);
	}
}
