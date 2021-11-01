package com.softicar.platform.core.module.replication.checksum;

import com.softicar.platform.common.math.Range;
import com.softicar.platform.db.core.table.DbTableName;

public class AGReplicationChecksum extends AGReplicationChecksumGenerated {

	public DbTableName getFullTableName() {

		return new DbTableName(getDatabaseName(), getTableName());
	}

	public Range<String> getChunkRange() {

		String lowerBoundary = getLowerBoundary();
		String upperBoundary = getUpperBoundary();
		if (lowerBoundary != null && upperBoundary != null) {
			return new Range<>(lowerBoundary, upperBoundary);
		} else {
			return null;
		}
	}
}
