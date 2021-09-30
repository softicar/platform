package com.softicar.platform.db.core.dbms.mysql;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.IDbConnection;
import com.softicar.platform.db.core.statement.DbStatement;
import java.sql.SQLException;

/**
 * This class provides some tweaks for the MySql database server.
 *
 * @author Oliver Richers
 */
public class DbMysqlTweaks {

	private static final int DEFAULT_GROUP_CONCAT_MAX_LENGTH = 1000000;
	private static final String MYSQL_PRODUCT_NAME = "MYSQL";
	private final IDbConnection connection;
	private final boolean mysql;

	public DbMysqlTweaks(IDbConnection connection) {

		this.connection = connection;
		this.mysql = checkIfMySqlServer();
	}

	private boolean checkIfMySqlServer() {

		try {
			String productName = connection.getMetaData().getDatabaseProductName();
			return productName.toUpperCase().trim().equals(MYSQL_PRODUCT_NAME);
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}

	public boolean isMysql() {

		return mysql;
	}

	/**
	 * Sets the maximum length for GROUP_CONCAT values.
	 * <p>
	 * This method does nothing if this is not a connection to a MySql server,
	 * that is, {@link #isMysql()} returns <i>false</i>.
	 *
	 * @param length
	 *            the maximum length in characters
	 */
	public DbMysqlTweaks setGroupConcatMaxLength(int length) {

		if (isMysql()) {
			new DbStatement("SET SESSION group_concat_max_len = %s", length).executeUncached();
		}
		return this;
	}

	/**
	 * Sets the maximum length for GROUP_CONCAT values to a sensible high value.
	 */
	@SuppressWarnings("resource")
	public static void setGroupConcatMaxLength() {

		new DbMysqlTweaks(DbConnections.getConnection()).setGroupConcatMaxLength(DEFAULT_GROUP_CONCAT_MAX_LENGTH);
	}
}
