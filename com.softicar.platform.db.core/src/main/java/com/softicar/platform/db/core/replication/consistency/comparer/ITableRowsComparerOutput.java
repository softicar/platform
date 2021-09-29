package com.softicar.platform.db.core.replication.consistency.comparer;

@FunctionalInterface
interface ITableRowsComparerOutput {

	void addMismatch(TableKeyRow keyRow, TableRow masterRow, TableRow slaveRow);

	default boolean hasEnoughMismatches() {

		return false;
	}
}
