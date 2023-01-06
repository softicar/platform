package com.softicar.platform.emf.log;

import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;

public interface IEmfChangeLogger<R extends IEmfTableRow<R, ?>> {

	void logChange(Collection<R> tableRows);
}
