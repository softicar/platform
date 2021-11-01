package com.softicar.platform.emf.action;

import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfManagementAction<R extends IEmfTableRow<R, ?>> extends IEmfAction<R> {

	void handleClick(R tableRow);
}
