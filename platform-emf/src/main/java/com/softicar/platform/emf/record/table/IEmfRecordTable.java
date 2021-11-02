package com.softicar.platform.emf.record.table;

import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.emf.record.IEmfRecord;
import com.softicar.platform.emf.table.IEmfTable;

public interface IEmfRecordTable<R extends IEmfRecord<R, P>, P, S> extends IEmfTable<R, P, S>, IDbRecordTable<R, P> {

	// nothing to add
}
