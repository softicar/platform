package com.softicar.platform.emf.log;

import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfChangeLogger<R extends IEmfTableRow<R, ?>> {

	void logChange(R tableRow);
}
