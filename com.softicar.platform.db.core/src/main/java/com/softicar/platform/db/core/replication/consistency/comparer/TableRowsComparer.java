package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.common.math.Range;
import com.softicar.platform.db.core.connection.IDbConnection;
import com.softicar.platform.db.core.table.definition.DbTableDefinition;
import java.util.Objects;

class TableRowsComparer {

	public static final int DEFAULT_CHUNK_SIZE = 100000;
	private final DbTableDefinition tableDefinitionA;
	private final DbTableDefinition tableDefinitionB;
	private final ITableRowsComparerOutput output;
	private TableRowGetter masterGetter;
	private TableRowGetter slaveGetter;
	private Range<TableKeyRow> comparisonRange;
	private int chunkSize;

	public TableRowsComparer(DbTableDefinition tableDefinitionA, DbTableDefinition tableDefinitionB, ITableRowsComparerOutput output) {

		this.tableDefinitionA = tableDefinitionA;
		this.tableDefinitionB = tableDefinitionB;
		this.output = output;
		this.chunkSize = DEFAULT_CHUNK_SIZE;

		// TODO assert tables have primary key
		// TODO assert tables have same column count
	}

	public TableRowsComparer setRange(Range<TableKeyRow> comparisonRange) {

		this.comparisonRange = comparisonRange;
		return this;
	}

	public TableRowsComparer setChunkSize(int chunkSize) {

		this.chunkSize = chunkSize;
		return this;
	}

	@SuppressWarnings("resource")
	public void compare(IDbConnection masterConnection, IDbConnection slaveConnection) {

		this.masterGetter = new TableRowGetter(tableDefinitionA, masterConnection, comparisonRange);
		this.slaveGetter = new TableRowGetter(tableDefinitionB, slaveConnection, comparisonRange);
		masterGetter.setLimit(chunkSize).nextTableRow();
		slaveGetter.setLimit(chunkSize).nextTableRow();
		while (true) {
			TableRow masterRow = masterGetter.getTableRow();
			TableRow slaveRow = slaveGetter.getTableRow();

			if (masterRow == null && slaveRow == null) {
				break;
			} else {
				compare(masterRow, slaveRow);
			}

			if (output.hasEnoughMismatches()) {
				break;
			}
		}
	}

	private void compare(TableRow masterRow, TableRow slaveRow) {

		if (Objects.equals(masterRow, slaveRow)) {
			masterGetter.nextTableRow();
			slaveGetter.nextTableRow();
		} else {
			if (masterRow != null && slaveRow != null) {
				TableKeyRow masterKey = masterRow.getPrimaryKeyRow();
				TableKeyRow slaveKey = slaveRow.getPrimaryKeyRow();
				if (Objects.equals(masterKey, slaveKey)) {
					handlePayloadMismatch(masterKey, masterRow, slaveRow);
				} else {
					if (slaveKey.compareTo(masterKey) < 0) {
						handleMissingMasterRow(slaveRow);
					} else {
						handleMissingSlaveRow(masterRow);
					}
				}
			} else {
				if (masterRow == null) {
					handleMissingMasterRow(slaveRow);
				} else {
					handleMissingSlaveRow(masterRow);
				}
			}
		}
	}

	private void handlePayloadMismatch(TableKeyRow keyRow, TableRow masterRow, TableRow slaveRow) {

		output.addMismatch(keyRow, masterRow, slaveRow);
		masterGetter.nextTableRow();
		slaveGetter.nextTableRow();
	}

	private void handleMissingMasterRow(TableRow slaveRow) {

		output.addMismatch(slaveRow.getPrimaryKeyRow(), null, slaveRow);
		slaveGetter.nextTableRow();
	}

	private void handleMissingSlaveRow(TableRow masterRow) {

		output.addMismatch(masterRow.getPrimaryKeyRow(), masterRow, null);
		masterGetter.nextTableRow();
	}
}
