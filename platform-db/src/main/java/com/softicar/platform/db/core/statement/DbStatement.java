package com.softicar.platform.db.core.statement;

import com.softicar.platform.db.core.DbMultiResultSetIterable;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.table.IDbCoreTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a database statement.
 *
 * @author Oliver Richers
 */
public final class DbStatement implements IDbStatement {

	private final StringBuilder textBuilder;
	private final Collection<Object> parameters;
	private final Set<IDbCoreTable> tables;

	/**
	 * Initialized an empty statement.
	 */
	public DbStatement() {

		this.textBuilder = new StringBuilder();
		this.parameters = new ArrayList<>();
		this.tables = new HashSet<>();
	}

	/**
	 * Initialized a statement with the given statement text.
	 *
	 * @param text
	 *            the statement text (never null)
	 * @param formatParameters
	 *            the {@link String#format} parameters
	 */
	public DbStatement(String text, Object...formatParameters) {

		this();
		addText(text, formatParameters);
	}

	// ------------------------------ read-only interface ------------------------------ //

	@Override
	public boolean isEmpty() {

		return textBuilder.length() == 0 && parameters.isEmpty();
	}

	@Override
	public String getText() {

		return textBuilder.toString();
	}

	@Override
	public Collection<Object> getParameters() {

		return parameters;
	}

	@Override
	public Set<IDbCoreTable> getTables() {

		return tables;
	}

	@Override
	public String toString() {

		return String.format("%s %s", getText(), getParameters());
	}

	// ------------------------------ mutation ------------------------------ //

	/**
	 * Appends the given text to this statement.
	 * <p>
	 * The text will be formatted using the {@link String#format} method.
	 * <p>
	 * The formatting is fail-safe in the sense that text formatting is only
	 * attempted when proper format parameters are given. Otherwise, the text
	 * will be appended literally as given.
	 *
	 * @param text
	 *            the text to add (never null)
	 * @param formatParameters
	 *            the {@link String#format} parameters
	 * @return this object
	 */
	public DbStatement addText(String text, Object...formatParameters) {

		if (formatParameters != null && formatParameters.length > 0) {
			textBuilder.append(String.format(text, formatParameters));
		} else {
			textBuilder.append(text);
		}
		return this;
	}

	/**
	 * Adds the given prepared statement parameter.
	 *
	 * @param parameter
	 *            the parameter to add (may be null)
	 * @return this object
	 */
	public DbStatement addParameter(Object parameter) {

		this.parameters.add(parameter);
		return this;
	}

	/**
	 * Adds the given prepared statement parameters.
	 * <p>
	 * The given collection may not be null, but the parameters in the
	 * collection may be null.
	 *
	 * @param parameters
	 *            the parameters to add (never null)
	 * @return this object
	 */
	public DbStatement addParameters(Collection<?> parameters) {

		this.parameters.addAll(parameters);
		return this;
	}

	/**
	 * Registers the given table to be referenced by this statement.
	 *
	 * @param table
	 *            the table to add (never null)
	 * @return this object
	 */
	public DbStatement addTable(IDbCoreTable table) {

		this.tables.add(table);
		return this;
	}

	/**
	 * Registers the given tables to be referenced by this statement.
	 *
	 * @param tables
	 *            the tables to add (never null)
	 * @return this object
	 */
	public DbStatement addTables(Collection<? extends IDbCoreTable> tables) {

		this.tables.addAll(tables);
		return this;
	}

	/**
	 * Append the given statement to this statement.
	 * <p>
	 * All text, parameters and tables will be added to this statement.
	 *
	 * @param statement
	 *            the statement to add (never null)
	 * @return this statement
	 */
	public DbStatement addStatement(DbStatement statement) {

		addText(statement.getText());
		addParameters(statement.getParameters());
		addTables(statement.getTables());
		return this;
	}

	// ------------------------------ execution ------------------------------ //

	/**
	 * Executes this statement on the current connection.
	 */
	public void execute() {

		DbConnections.accept(connection -> connection.execute(this));
	}

	/**
	 * Executes this statement on the current connection without caching.
	 */
	public void executeUncached() {

		DbConnections.accept(connection -> connection.executeUncached(this));
	}

	/**
	 * Executes this INSERT statement on the current connection.
	 *
	 * @return the generated ID of the inserted table row
	 */
	public int executeInsert() {

		return DbConnections.apply(connection -> connection.executeInsert(this));
	}

	/**
	 * Executes this multi-row INSERT statement on the current connection.
	 *
	 * @return the generated IDs of the inserted table rows (never null)
	 */
	public List<Integer> executeMultiRowInsert() {

		return DbConnections.apply(connection -> connection.executeMultiRowInsert(this));
	}

	/**
	 * Executes this SELECT statement on the current connection.
	 *
	 * @return the corresponding {@link DbResultSet} (never null)
	 */
	public DbResultSet executeQuery() {

		return DbConnections.apply(connection -> connection.executeQuery(this));
	}

	/**
	 * Executes this multi-SELECT statement on the current connection.
	 *
	 * @return the corresponding {@link DbMultiResultSetIterable} (never null)
	 */
	public DbMultiResultSetIterable executeMultiQuery() {

		return DbConnections.apply(connection -> connection.executeMultiQuery(this));
	}

	/**
	 * Executes this UPDATE statement on the current connection.
	 *
	 * @return the number of affected table rows
	 */
	public int executeUpdate() {

		return DbConnections.apply(connection -> connection.executeUpdate(this));
	}
}
