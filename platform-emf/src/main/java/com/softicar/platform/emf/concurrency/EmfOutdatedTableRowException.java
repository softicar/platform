package com.softicar.platform.emf.concurrency;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfOutdatedTableRowException extends SofticarUserException {

	private final IEmfTableRow<?, ?> tableRow;

	public EmfOutdatedTableRowException(IEmfTableRow<?, ?> tableRow) {

		super(EmfI18n.THE_ENTRY_IS_OUTDATED);

		this.tableRow = tableRow;
	}

	public IEmfTableRow<?, ?> getTableRow() {

		return tableRow;
	}
}
