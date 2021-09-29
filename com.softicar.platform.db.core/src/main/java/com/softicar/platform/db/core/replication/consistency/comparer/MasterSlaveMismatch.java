package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.core.connection.DbConnection;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.table.definition.DbTableDefinition;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MasterSlaveMismatch {

	private final DbTableDefinition tableDefinitionA;
	private final DbTableDefinition tableDefinitionB;
	private final TableKeyRow keyRow;
	private final TableRow masterRow;
	private final TableRow slaveRow;
	private boolean repaired;

	public MasterSlaveMismatch(DbTableDefinition tableDefinitionA, DbTableDefinition tableDefinitionB, TableKeyRow keyRow, TableRow masterRow,
			TableRow slaveRow) {

		this.tableDefinitionA = tableDefinitionA;
		this.tableDefinitionB = tableDefinitionB;
		this.keyRow = keyRow;
		this.masterRow = masterRow;
		this.slaveRow = slaveRow;
		this.repaired = false;
	}

	public Map<String, String> getKeyValueMap() {

		return keyRow.getValueMap();
	}

	public TableRow getMasterRow() {

		return masterRow;
	}

	public TableRow getSlaveRow() {

		return slaveRow;
	}

	public Map<String, String> getMasterValueMap() {

		return masterRow != null? masterRow.getValueMap() : null;
	}

	public Map<String, String> getSlaveValueMap() {

		return slaveRow != null? slaveRow.getValueMap() : null;
	}

	public Map<String, ValuePair> getDifferingValues() {

		TableRow rowA = masterRow != null? masterRow : new EmptyTableRow(tableDefinitionA);
		TableRow rowB = slaveRow != null? slaveRow : new EmptyTableRow(tableDefinitionB);
		return rowA.getDifferingValues(rowB);
	}

	@Override
	public String toString() {

		if (masterRow == null) {
			return String.format("%s missing master row %s", keyRow, slaveRow);
		} else if (slaveRow == null) {
			return String.format("%s missing slave row %s", keyRow, masterRow);
		} else {
			return String.format("%s %s", keyRow, Imploder.implode(getDifferingValuesAsStrings(), ", "));
		}
	}

	public void repairMismatchOnSlave(DbConnection connection) {

		DbStatement repairStatement = new MasterSlaveMismatchRepairer(tableDefinitionB.getTableName(), keyRow, masterRow, slaveRow).getRepairStatement();
		connection.executeUpdate(repairStatement);
	}

	public String getRepairStatement() {

		return new MasterSlaveMismatchRepairer(tableDefinitionB.getTableName(), keyRow, masterRow, slaveRow).getRepairStatement().toString();
	}

	public boolean isRepaired() {

		return repaired;
	}

	public void repairSlave(MasterSlaveComparer comparer) {

		if (!repaired) {
			try (DbConnection slaveConnection = comparer.createSlaveConnection()) {
				repairMismatchOnSlave(slaveConnection);
			}
			repaired = true;
		}
	}

	private Collection<String> getDifferingValuesAsStrings() {

		return getDifferingValues()//
			.entrySet()
			.stream()
			.map(this::getDifferingValuesAsString)
			.collect(Collectors.toList());
	}

	private String getDifferingValuesAsString(Entry<String, ValuePair> entry) {

		return String
			.format(//
				"(%s: MASTER='%s' SLAVE='%s')",
				entry.getKey().toString(),
				entry.getValue().getMasterValue(),
				entry.getValue().getSlaveValue());
	}

	public static class ValuePair {

		private final String masterValue;
		private final String slaveValue;

		public ValuePair(String masterValue, String slaveValue) {

			this.masterValue = masterValue;
			this.slaveValue = slaveValue;
		}

		public String getMasterValue() {

			return masterValue;
		}

		public String getSlaveValue() {

			return slaveValue;
		}
	}
}
