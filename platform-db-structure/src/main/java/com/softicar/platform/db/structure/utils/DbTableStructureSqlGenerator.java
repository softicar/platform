package com.softicar.platform.db.structure.utils;

import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.key.DbKeyType;
import com.softicar.platform.db.structure.key.IDbKeyStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates SQL statements from a given {@link IDbTableStructure}.
 * <p>
 * Particularly, <b>CREATE TABLE</b> and <b>CREATE SCHEMA</b> statements can be
 * generated.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbTableStructureSqlGenerator {

	private final IDbTableStructure tableStructure;
	private boolean setIfNotExists;
	private boolean skipForeignKeyCreation;
	private boolean isH2Database;
	private boolean humanReadable;
	private Integer autoIncrement;

	public DbTableStructureSqlGenerator(IDbTableStructure tableStructure) {

		this.tableStructure = tableStructure;
		this.setIfNotExists = false;
		this.skipForeignKeyCreation = false;
		this.isH2Database = false;
		this.humanReadable = false;
		this.autoIncrement = null;
	}

	/**
	 * Specifies whether <b>IF NOT EXISTS</b> shall be added to the generated
	 * <b>CREATE TABLE</b> statements.
	 *
	 * @param setIfNotExists
	 *            <i>true</i> if <b>CREATE TABLE</b> statements shall be
	 *            generated with <b>IF NOT EXISTS</b>; <i>false</i> otherwise
	 * @return this {@link DbTableStructureSqlGenerator}
	 */
	public DbTableStructureSqlGenerator setIfNotExists(boolean setIfNotExists) {

		this.setIfNotExists = setIfNotExists;
		return this;
	}

	/**
	 * Specifies whether foreign keys shall be omitted in generated <b>CREATE
	 * TABLE</b> statements.
	 *
	 * @param skipForeignKeyCreation
	 *            <i>true</i> if foreign keys shall be omitted in generated
	 *            <b>CREATE TABLE</b> statements; <i>false</i> otherwise
	 * @return this {@link DbTableStructureSqlGenerator}
	 */
	public DbTableStructureSqlGenerator setSkipForeignKeyCreation(boolean skipForeignKeyCreation) {

		this.skipForeignKeyCreation = skipForeignKeyCreation;
		return this;
	}

	/**
	 * Defines if the generated SQL statement shall be compatible to an H2
	 * database.
	 *
	 * @param isH2Database
	 *            <i>true</i> to be compatible; <i>false</i> otherwise
	 * @return this object
	 */
	public DbTableStructureSqlGenerator setIsH2Database(boolean isH2Database) {

		this.isH2Database = isH2Database;
		return this;
	}

	/**
	 * Toggles human readable output format.
	 * <p>
	 * If enabled, each column, key and foreign key definition is done on a
	 * separate line, indented by a tabulator character. Otherwise the complete
	 * statement will be generated in a single line.
	 *
	 * @param humanReadable
	 *            <i>true</i> to enable human readable format; <i>false</i> to
	 *            disabled it
	 * @return this object
	 */
	public DbTableStructureSqlGenerator setHumanReadable(boolean humanReadable) {

		this.humanReadable = humanReadable;
		return this;
	}

	/**
	 * Defines the initial auto-increment value to be used.
	 *
	 * @param autoIncrement
	 *            the auto-increment value (may be null)
	 * @return this object
	 */
	public DbTableStructureSqlGenerator setAutoIncrement(Integer autoIncrement) {

		this.autoIncrement = autoIncrement;
		return this;
	}

	/**
	 * Generates a <b>CREATE TABLE</b> statement.
	 *
	 * @return the generated <b>CREATE TABLE</b> statement (never null)
	 * @see #setIfNotExists(boolean)
	 * @see #setSkipForeignKeyCreation(boolean)
	 */
	public String getCreateTableStatement() {

		return new StringBuilder()
			.append("CREATE TABLE ")
			.append(setIfNotExists? "IF NOT EXISTS " : "")
			.append(tableStructure.getTableName().getQuoted())
			.append(" (")
			.append(humanReadable? "\n\t" : "")
			.append(Imploder.implode(getCreateTableRows(), humanReadable? ",\n\t" : ","))
			.append(humanReadable? "\n" : "")
			.append(")")
			.append(getCommentToken())
			.append(";")
			.toString();
	}

	/**
	 * Generates a <b>CREATE SCHEMA</b> statement.
	 *
	 * @return the generated <b>CREATE SCHEMA</b> statement (never null)
	 */
	public String getCreateDatabaseStatement() {

		String databaseName = tableStructure.getTableName().getDatabaseName();
		return String.format("CREATE SCHEMA IF NOT EXISTS `%s`", databaseName);
	}

	private List<String> getCreateTableRows() {

		List<String> rows = new ArrayList<>();
		rows.addAll(getCreateTableColumnRows());
		rows.addAll(getCreateTableKeyRows());
		if (!skipForeignKeyCreation) {
			rows.addAll(getCreateTableForeignKeyRows());
		}
		return rows;
	}

	private List<String> getCreateTableColumnRows() {

		return tableStructure//
			.getColumns()
			.stream()
			.map(this::getColumnSqlDefinition)
			.collect(Collectors.toList());
	}

	private String getCommentToken() {

		String comment = tableStructure.getComment();
		if (!comment.isEmpty()) {
			return " COMMENT='" + comment.replace("'", "''") + "'";
		} else {
			return "";
		}
	}

	private String getColumnSqlDefinition(IDbColumnStructure column) {

		return new DbColumnStructureSqlGenerator(column)//
			.setIsH2Database(isH2Database)
			.setAutoIncrementStart(autoIncrement)
			.toString();
	}

	private List<String> getCreateTableKeyRows() {

		return tableStructure//
			.getKeys()
			.stream()
			.filter(this::isValidKeyStructure)
			.map(this::generateKeyStructureSql)
			.collect(Collectors.toList());
	}

	private boolean isValidKeyStructure(IDbKeyStructure structure) {

		if (isH2Database) {
			return !structure.getType().equals(DbKeyType.INDEX);
		} else {
			return true;
		}
	}

	private String generateKeyStructureSql(IDbKeyStructure keyStructure) {

		return new DbKeyStructureSqlGenerator(keyStructure).setH2Database(isH2Database).toString();
	}

	private List<String> getCreateTableForeignKeyRows() {

		return tableStructure//
			.getForeignKeys()
			.stream()
			.map(DbForeignKeyStructureSqlGenerator::new)
			.map(DbForeignKeyStructureSqlGenerator::toString)
			.collect(Collectors.toList());
	}
}
