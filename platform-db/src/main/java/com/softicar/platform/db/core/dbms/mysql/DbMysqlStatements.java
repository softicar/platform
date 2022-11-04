package com.softicar.platform.db.core.dbms.mysql;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.connection.IDbConnection;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.table.DbTableName;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Special statement execution methods for MySQL database servers.
 * <p>
 * The methods of this class should be used with care and only when absolutely
 * necessary. Most of the methods are only working on MySQL, which means that we
 * will need to find alternatives when we want to become DBMS agnostic.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class DbMysqlStatements {

	private static final int VALUE_COLUMN_INDEX_FOR_SHOW_VARIABLE = 2;

	/**
	 * Executes the MySQL statement <i>CREATE USER</i> with the given
	 * parameters.
	 *
	 * @param username
	 *            the name of the user (never <i>null</i>)
	 * @param hostname
	 *            the name or address of the host or just <i>%</i> (never
	 *            <i>null</i>)
	 * @param password
	 *            the password (never <i>null</i>)
	 */
	public static void createUser(String username, String hostname, String password) {

		executeUncached("CREATE USER '%s'@'%s' IDENTIFIED BY '%s'", username, hostname, password);
	}

	/**
	 * Executes the MySQL statement <i>GRANT</i> with the given parameters.
	 *
	 * @param username
	 *            the name of the user (never <i>null</i>)
	 * @param hostname
	 *            the name or address of the host or just <i>%</i> (never
	 *            <i>null</i>)
	 * @param database
	 *            the name of the database (never <i>null</i>)
	 * @param privileges
	 *            the privileges to grant
	 */
	public static void grant(String username, String hostname, String database, Collection<DbMysqlPrivilege> privileges) {

		String privilegeString = privileges.stream().map(DbMysqlPrivilege::getIdentifier).collect(Collectors.joining(","));
		executeUncached("GRANT %s ON %s.* TO '%s'@'%s'", privilegeString, database, username, hostname);
	}

	/**
	 * Executes the MySQL statement <i>SHOW CREATE TABLE</i> for the given
	 * table.
	 *
	 * @param tableName
	 *            the table name (never <i>null</i>)
	 * @return the result set (never <i>null</i>)
	 */
	public static DbResultSet showCreateTable(DbTableName tableName) {

		Objects.requireNonNull(tableName);
		DbMysqlIdentifierValidator.assertValidDatabaseName(tableName.getDatabaseName());
		DbMysqlIdentifierValidator.assertValidSimpleTableName(tableName.getSimpleName());

		return executeQuery("SHOW CREATE TABLE " + tableName);
	}

	/**
	 * Executes the MySQL statement <i>SHOW TABLE STATUS FROM</i> for the given
	 * database.
	 *
	 * @param databaseName
	 *            the database name (never <i>null</i>)
	 * @return the result set (never <i>null</i>)
	 */
	public static DbResultSet showTableStatusFrom(String databaseName) {

		DbMysqlIdentifierValidator.assertValidDatabaseName(databaseName);

		return executeQuery("SHOW TABLE STATUS FROM `" + databaseName + "`");
	}

	/**
	 * Executes the MySQL statement <i>SHOW VARIABLES</i> for the given
	 * variable.
	 *
	 * @param variableName
	 *            the name of the variable (never <i>null</i>)
	 * @return the result set (never <i>null</i>)
	 */
	public static DbResultSet showVariable(String variableName) {

		DbMysqlIdentifierValidator.assertValidVariableName(variableName);

		return executeQuery(String.format("SHOW VARIABLES WHERE `Variable_name` = '%s'", variableName));
	}

	/**
	 * Gets the value of the MySQL server variable with the given name. If there
	 * is no matching variable, an {@link Optional#empty()} is returned.
	 *
	 * @param variable
	 *            the name of the variable (never <i>null</i>)
	 * @return the value of the variable as optional string
	 */
	public static Optional<String> getVariableValue(String variable) {

		try (DbResultSet resultSet = showVariable(variable)) {
			return resultSet.next()? Optional.of(resultSet.getString(VALUE_COLUMN_INDEX_FOR_SHOW_VARIABLE)) : Optional.empty();
		}
	}

	/**
	 * Gets the current UNIX timestamp from the MySQL server.
	 *
	 * @return the UNIX timestamp
	 */
	public static int getUnixTimestamp(IDbConnection connection) {

		try (DbResultSet resultSet = connection.executeQuery(new DbStatement("SELECT UNIX_TIMESTAMP()"))) {
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new SofticarException("Failed to get unix timestamp from MySQL server.");
			}
		}
	}

	/**
	 * Executes the MySQL statement <i>KILL</i> with the given process ID.
	 *
	 * @param processId
	 *            the ID of the process to kill
	 */
	public static void kill(int processId) {

		executeUncached("KILL %s", processId);
	}

	/**
	 * Executes the MySQL statement <i>START TRANSACTION WITH CONSISTENT
	 * SNAPSHOT</i>.
	 *
	 * @param connection
	 *            the connection to use (never <i>null</i>)
	 */
	public static void startTransactionWithConsistentSnapshot(IDbConnection connection) {

		connection.execute(new DbStatement("START TRANSACTION WITH CONSISTENT SNAPSHOT"));
	}

	/**
	 * Executes the MySQL statement <i>SHOW DATABASES</i>.
	 *
	 * @return the result set (never <i>null</i>)
	 */
	public static DbResultSet showDatabases() {

		return executeQuery("SHOW DATABASES");
	}

	/**
	 * Determines the names of all databases in the connected MySQL server.
	 *
	 * @return a {@link Set} of strings that contains all database names (never
	 *         <i>null</i>)
	 */
	public static Set<String> getDatabaseNames() {

		Set<String> databaseNames = new TreeSet<>();
		try (DbResultSet resultSet = DbMysqlStatements.showDatabases()) {
			while (resultSet.next()) {
				databaseNames.add(resultSet.getString(1));
			}
		}
		return databaseNames;
	}

	/**
	 * Executes the MySQL statement <i>SET GLOBAL SQL_SLAVE_SKIP_COUNTER</i>
	 * with the given value.
	 *
	 * @param value
	 *            the value to set
	 */
	public static void setGlobalSqlSlaveSkipCounter(int value) {

		executeUncached("SET GLOBAL SQL_SLAVE_SKIP_COUNTER = %s", value);
	}

	/**
	 * Executes the MySQL statement <i>START SLAVE</i>.
	 */
	public static void startSlave() {

		executeUncached("START SLAVE");
	}

	/**
	 * Executes the MySQL statement <i>SHOW PROCESSLIST</i>.
	 *
	 * @return the result set (never <i>null</i>)
	 */
	public static DbResultSet showProcessList() {

		return executeQuery("SHOW PROCESSLIST");
	}

	/**
	 * Executes the MySQL statement <i>SHOW FULL PROCESSLIST</i>.
	 *
	 * @return the result set (never <i>null</i>)
	 */
	public static DbResultSet showFullProcessList() {

		return executeQuery("SHOW FULL PROCESSLIST");
	}

	/**
	 * Executes the MySQL statement <i>SHOW MASTER STATUS</i>.
	 *
	 * @return the result set (never <i>null</i>)
	 */
	public static DbResultSet showMasterStatus() {

		return executeQuery("SHOW MASTER STATUS");
	}

	/**
	 * Executes the MySQL statement <i>SHOW SLAVE STATUS</i>.
	 *
	 * @return the result set (never <i>null</i>)
	 */
	public static DbResultSet showSlaveStatus() {

		return executeQuery("SHOW SLAVE STATUS");
	}

	/**
	 * Enables or disables foreign-key checks.
	 *
	 * @param enabled
	 *            <i>true</i> if foreign-key checks shall be enabled;
	 *            <i>false</i> otherwise
	 */
	public static void setForeignKeyChecksEnabled(boolean enabled) {

		executeUncached("SET FOREIGN_KEY_CHECKS=%s", enabled? 1 : 0);
	}

	/**
	 * Drops the given database.
	 *
	 * @param databaseName
	 *            the name of the database to drop (never <i>null</i>)
	 */
	public static void dropDatabase(String databaseName) {

		DbMysqlIdentifierValidator.assertValidDatabaseName(databaseName);
		executeUncached("DROP DATABASE `%s`", databaseName);
	}

	/**
	 * Drops the given table.
	 *
	 * @param tableName
	 *            the table to drop (never <i>null</i>)
	 */
	public static void dropTable(DbTableName tableName) {

		Objects.requireNonNull(tableName);
		executeUncached("DROP TABLE %s", tableName.getQuoted());
	}

	/**
	 * Removes all rows from the given table.
	 *
	 * @param tableName
	 *            the table to truncate (never <i>null</i>)
	 */
	public static void truncateTable(DbTableName tableName) {

		Objects.requireNonNull(tableName);
		executeUncached("TRUNCATE TABLE %s", tableName.getQuoted());
	}

	/**
	 * Adds SQL mode {@code NO_AUTO_VALUE_ON_ZERO} to the modes of the current
	 * session.
	 */
	public static void addSqlModeNoAutoValueOnZero() {

		new DbStatement("SET SESSION SQL_MODE = CONCAT((SELECT @@SESSION.SQL_MODE), ',NO_AUTO_VALUE_ON_ZERO');").executeUncached();
	}

	// ------------------------------ private ------------------------------ //

	private static DbResultSet executeQuery(String query) {

		return new DbStatement(query).executeQuery();
	}

	private static void executeUncached(String statement, Object...arguments) {

		new DbStatement(statement, arguments).executeUncached();
	}
}
