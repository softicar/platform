package com.softicar.platform.emf.delete;

import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfDeleteStrategy<R extends IEmfTableRow<R, ?>> {

	boolean isDeletable(R tableRow);

	void delete(R tableRow);
}
