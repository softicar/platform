package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.common.math.Range;
import com.softicar.platform.db.core.table.DbTableName;

/**
 * Configuration interface for {@link MasterSlaveComparer}.
 *
 * @author Oliver Richers
 */
public interface IMasterSlaveComparerConfiguration {

	/**
	 * Returns the name of the database table to compare.
	 *
	 * @return the database table name (never null)
	 */
	DbTableName getTableName();

	/**
	 * Returns the address of the master server.
	 *
	 * @return the master server address (never null)
	 */
	String getMasterAddress();

	/**
	 * Returns the address of the slave server.
	 *
	 * @return the slave server address (never null)
	 */
	String getSlaveAddress();

	/**
	 * Returns the range for the table row subset to compare.
	 * <p>
	 * This range defines the lower and the upper boundary to use in the WHERE
	 * condition, limiting the comparison to a subset of the table rows. The
	 * lower and upper bounds define the minimum or the maximum value of the
	 * primary key column values. The range is inclusive.
	 * <p>
	 * The multi-value bounds must be given in comma separated form.
	 * <p>
	 * This method may return <i>NULL</i> if all table rows shall be compared.
	 *
	 * @return the comparison range (may be null)
	 */
	Range<String> getComparisonRange();
}
