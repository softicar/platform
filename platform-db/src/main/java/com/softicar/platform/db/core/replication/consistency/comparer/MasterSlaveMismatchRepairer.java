package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.table.DbTableName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

public class MasterSlaveMismatchRepairer {

	private final DbTableName tableName;
	private final TableKeyRow keyRow;
	private final TableRow masterRow;
	private final TableRow slaveRow;

	public MasterSlaveMismatchRepairer(DbTableName tableName, TableKeyRow keyRow, TableRow masterRow, TableRow slaveRow) {

		this.tableName = tableName;
		this.keyRow = keyRow;
		this.masterRow = masterRow;
		this.slaveRow = slaveRow;
	}

	public DbStatement getRepairStatement() {

		if (masterRow == null) {
			return getDeleteStatement();
		} else if (slaveRow == null) {
			return getInsertStatement();
		} else {
			return getUpdateStatement();
		}
	}

	private DbStatement getDeleteStatement() {

		return new DbStatement()//
			.addText("DELETE FROM %s ", tableName)
			.addText("WHERE (%s) = %s", keyRow.getImplodedColumnNames(), keyRow.getQuestionMarkList())
			.addParameters(keyRow.getValues());
	}

	private DbStatement getInsertStatement() {

		return new DbStatement()//
			.addText("INSERT INTO %s ", tableName)
			.addText("(%s) VALUES %s", masterRow.getImplodedColumnNames(), masterRow.getQuestionMarkList())
			.addParameters(masterRow.getValues());
	}

	private DbStatement getUpdateStatement() {

		Collection<String> keyColumnNames = keyRow.getColumns();
		Collection<String> assignments = new ArrayList<>();
		Collection<String> assignmentParameters = new ArrayList<>();
		for (Entry<String, String> entry: masterRow.getValueMap().entrySet()) {
			if (!keyColumnNames.contains(entry.getKey())) {
				assignments.add("`" + entry.getKey() + "` = ?");
				assignmentParameters.add(entry.getValue());
			}
		}

		return new DbStatement()//
			.addText("UPDATE %s ", tableName)
			.addText("SET ")
			.addText(Imploder.implode(assignments, ", "))
			.addParameters(assignmentParameters)
			.addText(" WHERE (%s) = %s", keyRow.getImplodedColumnNames(), keyRow.getQuestionMarkList())
			.addParameters(keyRow.getValues());
	}
}
