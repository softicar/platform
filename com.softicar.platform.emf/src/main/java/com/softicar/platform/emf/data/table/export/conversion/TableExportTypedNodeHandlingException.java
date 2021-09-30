package com.softicar.platform.emf.data.table.export.conversion;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.emf.data.table.export.node.ITableExportManipulatedNode;

public class TableExportTypedNodeHandlingException extends SofticarDeveloperException {

	public TableExportTypedNodeHandlingException(ITableExportManipulatedNode node) {

		super("Don't know how to handle the given %s of type '%s'.", ITableExportManipulatedNode.class.getSimpleName(), node.getClass().getCanonicalName());
	}
}
