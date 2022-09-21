package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.common.math.Range;
import com.softicar.platform.db.core.DbProperties;
import com.softicar.platform.db.core.connection.DbConnection;
import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.dbms.mysql.DbMysqlStatements;
import com.softicar.platform.db.core.table.definition.DbTableDefinition;
import com.softicar.platform.db.core.table.metadata.DbMysqlTableMetadata;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MasterSlaveComparer implements ITableRowsComparerOutput {

	private static final int DEFAULT_MAXIMUM_MISMATCHES = 100;
	private final IMasterSlaveComparerConfiguration configuration;
	private final DbTableDefinition tableDefinition;
	private final List<MasterSlaveMismatch> mismatches;
	private int maximumMismatches;
	private int chunkSize;

	public MasterSlaveComparer(IMasterSlaveComparerConfiguration configuration) {

		this.configuration = configuration;
		this.tableDefinition = new DbTableDefinition(configuration.getTableName(), DbMysqlTableMetadata::new);
		this.mismatches = new ArrayList<>();
		this.maximumMismatches = DEFAULT_MAXIMUM_MISMATCHES;
		this.chunkSize = TableRowsComparer.DEFAULT_CHUNK_SIZE;
	}

	public MasterSlaveComparer setMaximumMismatches(int maximumMismatches) {

		this.maximumMismatches = maximumMismatches;
		return this;
	}

	public MasterSlaveComparer setChunkSize(int chunkSize) {

		this.chunkSize = chunkSize;
		return this;
	}

	public MasterSlaveComparer compare() {

		try (DbConnection masterConnection = createMasterConnection(); DbConnection slaveConnection = createSlaveConnection()) {
			DbMysqlStatements.startTransactionWithConsistentSnapshot(masterConnection);
			DbMysqlStatements.startTransactionWithConsistentSnapshot(slaveConnection);
			new TableRowsComparer(tableDefinition, tableDefinition, this)//
				.setRange(getComparisonRange())
				.setChunkSize(chunkSize)
				.compare(masterConnection, slaveConnection);
		}
		return this;
	}

	public Collection<MasterSlaveMismatch> getMismatches() {

		return mismatches;
	}

	@Override
	public void addMismatch(TableKeyRow keyRow, TableRow masterRow, TableRow slaveRow) {

		mismatches.add(new MasterSlaveMismatch(tableDefinition, tableDefinition, keyRow, masterRow, slaveRow));
	}

	@Override
	public boolean hasEnoughMismatches() {

		return mismatches.size() >= maximumMismatches;
	}

	public DbConnection createMasterConnection() {

		return createConnection(configuration.getMasterAddress());
	}

	public DbConnection createSlaveConnection() {

		return createConnection(configuration.getSlaveAddress());
	}

	public DbTableDefinition getTableDefinition() {

		return tableDefinition;
	}

	private DbConnection createConnection(String address) {

		return new DbConnection(//
			DbServerType.MYSQL,
			address,
			"",
			DbProperties.USERNAME.getValue(),
			DbProperties.PASSWORD.getValue());
	}

	private Range<TableKeyRow> getComparisonRange() {

		Range<String> comparisonRange = configuration.getComparisonRange();
		if (comparisonRange != null) {
			return new Range<>(//
				parseValuesIntoTableKeyRow(comparisonRange.getMin()),
				parseValuesIntoTableKeyRow(comparisonRange.getMax()));
		} else {
			return null;
		}
	}

	private TableKeyRow parseValuesIntoTableKeyRow(String values) {

		return new TableKeyRow(tableDefinition.getPrimaryKey(), Arrays.asList(values.split(",")));
	}
}
