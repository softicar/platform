package com.softicar.platform.emf.record;

import com.softicar.platform.db.runtime.record.IDbRecord;
import com.softicar.platform.emf.record.table.IEmfRecordTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * An {@link IEmfRecord} is an EMF extension of {@link IDbRecord}.
 *
 * @author Oliver Richers
 */
public interface IEmfRecord<R extends IEmfRecord<R, P>, P> extends IEmfTableRow<R, P>, IDbRecord<R, P> {

	@Override
	IEmfRecordTable<R, P, ?> table();
}
