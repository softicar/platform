package com.softicar.platform.db.runtime.table.row;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.db.runtime.field.IDbField;

public class DbMissingFieldValueException extends SofticarDeveloperException {

	private final IDbTableRow<?, ?> tableRow;
	private final IDbField<?, ?> field;

	public DbMissingFieldValueException(IDbTableRow<?, ?> tableRow, IDbField<?, ?> field) {

		super("Table row of type '%s' is missing value for field '%s'.".formatted(tableRow.getClass().getSimpleName(), field.getTitle()));

		this.tableRow = tableRow;
		this.field = field;
	}

	public IDbTableRow<?, ?> getTableRow() {

		return tableRow;
	}

	public IDbField<?, ?> getField() {

		return field;
	}
}
