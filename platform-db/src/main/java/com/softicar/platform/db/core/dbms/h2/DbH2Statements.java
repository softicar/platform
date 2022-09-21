package com.softicar.platform.db.core.dbms.h2;

import com.softicar.platform.db.core.statement.DbStatement;
import java.util.Objects;

/**
 * Special statement execution methods for H2 database servers.
 * <p>
 * The methods of this class should be used with care and only when absolutely
 * necessary. Most of the methods are only working on H2.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class DbH2Statements {

	/**
	 * Executes the H2 statement <i>SET COLLATION</i> with the given collation.
	 *
	 * @param collation
	 *            the collation to use (never null)
	 */
	public static void setCollation(String collation) {

		executeUncached("SET COLLATION " + Objects.requireNonNull(collation));
	}

	// ------------------------------ private ------------------------------ //

	private static void executeUncached(String statement) {

		new DbStatement(statement).executeUncached();
	}
}
