package com.softicar.platform.emf.delete;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfDeleteStrategyBuilder<R extends IEmfTableRow<R, ?>> {

	private final EmfDeleteStrategy<R> strategy = new EmfDeleteStrategy<>();

	public EmfDeleteStrategyBuilder<R> setDeleteFromTable() {

		strategy.setDeleteFromTable();
		return this;
	}

	public EmfDeleteStrategyBuilder<R> setDeletedFlag(IDbField<R, Boolean> deletedFlag) {

		strategy.setDeletedFlag(deletedFlag);
		return this;
	}

	public IEmfDeleteStrategy<R> build() {

		strategy.validate();
		return strategy;
	}
}
