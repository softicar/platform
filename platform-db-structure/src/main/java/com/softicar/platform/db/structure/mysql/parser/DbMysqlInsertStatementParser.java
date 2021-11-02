package com.softicar.platform.db.structure.mysql.parser;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.db.core.sql.parser.DbSqlScanner;
import com.softicar.platform.db.core.table.DbTableName;
import java.util.ArrayList;
import java.util.List;

public class DbMysqlInsertStatementParser {

	private final String statement;
	private DbSqlScanner scanner;
	private TablePart tablePart;
	private RowsPart rowsPart;

	public DbMysqlInsertStatementParser(String statement) {

		this.statement = statement;
		this.scanner = null;
		this.tablePart = null;
		this.rowsPart = null;
	}

	public DbMysqlParsedInsertStatement parse() {

		this.scanner = new DbSqlScanner(statement);
		this.tablePart = new TablePart();
		this.rowsPart = new RowsPart();

		validateRows();

		return new DbMysqlParsedInsertStatement(//
			new DbTableName(tablePart.databaseName, tablePart.tableName),
			tablePart.columnNames,
			rowsPart.rows);
	}

	private void validateRows() {

		int columns = tablePart.columnNames.size();
		for (int i = 0; i < rowsPart.rows.size(); i++) {
			List<String> row = rowsPart.rows.get(i);
			if (row.size() != columns) {
				throw new SofticarException("Expected %s columns but encountered %s in the row with index %s.", columns, row.size(), i);
			}
		}
	}

	private class TablePart {

		protected final String databaseName;
		protected final String tableName;
		protected final List<String> columnNames;

		public TablePart() {

			this.columnNames = new ArrayList<>();

			scanner.assertToken("INSERT");
			scanner.assertToken("INTO");
			this.databaseName = scanner.getTokenBetween("`");
			scanner.assertToken(".");
			this.tableName = scanner.getTokenBetween("`");
			scanner.assertToken("(");
			while (true) {
				if (scanner.testCurrentToken(")")) {
					break;
				} else if (scanner.testCurrentToken(",")) {
					scanner.consumeCurrentToken();
				} else {
					columnNames.add(scanner.getTokenBetween("`"));
				}
			}
			scanner.assertToken(")");
		}
	}

	private class RowsPart {

		protected final List<List<String>> rows;

		public RowsPart() {

			this.rows = new ArrayList<>();

			scanner.assertToken("VALUES");
			while (scanner.hasMoreTokens()) {
				if (scanner.testCurrentToken(";") || !scanner.hasMoreTokens()) {
					break;
				} else if (scanner.testCurrentToken(",")) {
					scanner.consumeCurrentToken();
				} else {
					scanner.assertToken("(");
					List<String> values = new ArrayList<>();
					rows.add(values);
					while (scanner.hasMoreTokens()) {
						if (scanner.testCurrentToken(")")) {
							break;
						} else if (scanner.testCurrentToken(",")) {
							scanner.consumeCurrentToken();
						} else if (scanner.testCurrentToken("'")) {
							values.add(scanner.getTokenBetween("'"));
						} else {
							values.add(scanner.consumeCurrentToken());
						}
					}
					scanner.assertToken(")");
				}
			}
		}
	}
}
