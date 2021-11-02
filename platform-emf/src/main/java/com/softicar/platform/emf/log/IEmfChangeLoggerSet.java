package com.softicar.platform.emf.log;

import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;

public interface IEmfChangeLoggerSet<R extends IEmfTableRow<R, ?>> {

	Collection<IEmfChangeLogger<R>> getLoggers();
}
