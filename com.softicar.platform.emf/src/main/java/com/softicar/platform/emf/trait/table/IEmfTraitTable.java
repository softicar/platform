package com.softicar.platform.emf.trait.table;

import com.softicar.platform.emf.record.table.IEmfRecordTable;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.trait.IEmfTrait;

public interface IEmfTraitTable<T extends IEmfTrait<T, R>, R extends IEmfTableRow<R, ?>> extends IEmfRecordTable<T, R, R> {

	IEmfTable<R, ?, ?> getTargetTable();
}
