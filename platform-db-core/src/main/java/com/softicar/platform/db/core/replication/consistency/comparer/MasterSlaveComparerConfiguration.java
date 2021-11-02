package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.common.math.Range;
import com.softicar.platform.db.core.table.DbTableName;

/**
 * Default implementation of {@link IMasterSlaveComparerConfiguration}.
 *
 * @author Oliver Richers
 */
public class MasterSlaveComparerConfiguration implements IMasterSlaveComparerConfiguration {

	private DbTableName tableName;
	private String masterAddress;
	private String slaveAddress;
	private Range<String> comparisonRange;

	@Override
	public DbTableName getTableName() {

		return tableName;
	}

	@Override
	public String getMasterAddress() {

		return masterAddress;
	}

	@Override
	public String getSlaveAddress() {

		return slaveAddress;
	}

	@Override
	public Range<String> getComparisonRange() {

		return comparisonRange;
	}

	// ------------------------------ mutating methods ------------------------------ //

	public MasterSlaveComparerConfiguration setTableName(DbTableName tableName) {

		this.tableName = tableName;
		return this;
	}

	public MasterSlaveComparerConfiguration setMasterAddress(String masterAddress) {

		this.masterAddress = masterAddress;
		return this;
	}

	public MasterSlaveComparerConfiguration setSlaveAddress(String slaveAddress) {

		this.slaveAddress = slaveAddress;
		return this;
	}

	public MasterSlaveComparerConfiguration setComparisonRange(Range<String> comparisonRange) {

		this.comparisonRange = comparisonRange;
		return this;
	}
}
