package com.softicar.platform.db.structure.mysql.parser;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.core.sql.parser.DbSqlScanner;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import com.softicar.platform.db.structure.enums.DbEnumTables;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;
import com.softicar.platform.db.structure.key.DbKeyType;
import com.softicar.platform.db.structure.mysql.DbMysqlForeignKeyStructure;
import com.softicar.platform.db.structure.mysql.DbMysqlKeyStructure;
import com.softicar.platform.db.structure.mysql.column.DbMysqlColumnStructure;
import com.softicar.platform.db.structure.table.DbTableStructure;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbMysqlCreateTableStatementParser {

	private final String statement;
	private DbSqlScanner scanner;
	private StatementHead statementHead;
	private StatementBody statementBody;
	private StatementTail statementTail;
	private String fallbackDatabaseName;

	public DbMysqlCreateTableStatementParser(String statement) {

		this.statement = statement;
		this.fallbackDatabaseName = "";
	}

	public DbMysqlCreateTableStatementParser setFallbackDatabaseName(String fallbackDatabaseName) {

		this.fallbackDatabaseName = fallbackDatabaseName;
		return this;
	}

	public DbTableStructure parse() {

		this.scanner = new DbSqlScanner(statement);
		this.statementHead = new StatementHead();
		this.statementBody = new StatementBody();
		this.statementTail = new StatementTail();

		DbTableStructure tableStructure = new DbTableStructure(getTableName());
		statementBody.columnRows.stream().forEach(row -> tableStructure.addColumnStructure(new DbMysqlColumnStructure(tableStructure, row)));
		statementBody.keyRows.stream().forEach(row -> tableStructure.addKeyStructure(new DbMysqlKeyStructure(tableStructure, row)));
		statementBody.foreignKeyRows.stream().forEach(row -> tableStructure.addForeignKeyStructure(new DbMysqlForeignKeyStructure(tableStructure, row)));
		tableStructure.setComment(statementTail.comment);
		tableStructure.setEnumTable(isEnumTable());
		tableStructure.validate();
		return tableStructure;
	}

	private DbTableName getTableName() {

		String databaseName = Optional.ofNullable(statementHead.databaseName).orElse(fallbackDatabaseName);
		return new DbTableName(databaseName, statementHead.tableName);
	}

	private boolean isEnumTable() {

		return statementTail.comment.contains(DbEnumTables.MARKER_COMMENT);
	}

	public class ColumnRow {

		private final String columnName;
		private final String typeName;
		private final List<String> typeParameters = new ArrayList<>();
		private boolean nullAllowed = true;
		private boolean autoIncrement = false;
		private boolean onUpdateNow;
		private boolean unsigned = false;
		private String characterSet;
		private String collation;
		private DefaultValue defaultValue;
		private String comment;

		public String getColumnName() {

			return columnName;
		}

		public String getTypeName() {

			return typeName;
		}

		public List<String> getTypeParameters() {

			return typeParameters;
		}

		public boolean isNullAllowed() {

			return nullAllowed;
		}

		public boolean isAutoIncrement() {

			return autoIncrement;
		}

		public DbColumnDefaultType getDefaultType() {

			return defaultValue != null? defaultValue.getType() : DbColumnDefaultType.NONE;
		}

		public String getDefaultValue() {

			return defaultValue != null? defaultValue.getValue() : null;
		}

		public boolean isOnUpdateNow() {

			return onUpdateNow;
		}

		public boolean isUnsigned() {

			return unsigned;
		}

		public String getCharacterSet() {

			return characterSet;
		}

		public String getCollation() {

			return collation;
		}

		public String getComment() {

			return comment;
		}

		public ColumnRow() {

			// column name
			this.columnName = scanner.getTokenBetween("`");

			// column type
			this.typeName = scanner.consumeCurrentToken();

			// column type parameter
			if (typeName.equalsIgnoreCase("enum") || typeName.equalsIgnoreCase("set")) {
				scanner.assertToken("(");
				while (true) {
					this.typeParameters.add(scanner.getTokenBetween("'"));
					if (scanner.testCurrentToken(")")) {
						break;
					}
					scanner.assertToken(",");
				}
				scanner.assertToken(")");
			} else if (typeName.equalsIgnoreCase("decimal")
					|| scanner.testCurrentToken("(") && (typeName.equalsIgnoreCase("double") || typeName.equalsIgnoreCase("float"))) {
				scanner.assertToken("(");
				while (true) {
					this.typeParameters.add(scanner.consumeCurrentToken());
					if (scanner.testCurrentToken(")")) {
						break;
					}
					scanner.assertToken(",");
				}
				scanner.assertToken(")");
			} else if (scanner.testCurrentToken("(")) {
				this.typeParameters.add(scanner.getTokenBetween("(", ")"));
			}

			// column arguments
			while (true) {
				if (scanner.testCurrentToken(",") || scanner.testCurrentToken(")")) {
					// meaning: end of column definition
					break;
				} else if (scanner.testCurrentToken("NOT")) {
					// meaning: not null
					scanner.assertTokens("NOT", "NULL");
					this.nullAllowed = false;
				} else if (scanner.testCurrentToken("NULL")) {
					// meaning: null
					scanner.assertTokens("NULL");
					this.nullAllowed = true;
				} else if (scanner.testCurrentToken("default")) {
					// meaning: default value
					this.defaultValue = new DefaultValue();
				} else if (scanner.testCurrentToken("auto_increment")) {
					// meaning: auto increment
					scanner.consumeCurrentToken();
					// ignore H2-style auto-increment value in parenthesis, like "AUTO_INCREMENT(123)"
					if (scanner.testCurrentToken("(")) {
						scanner.getTokenBetween("(", ")");
					}
					this.autoIncrement = true;
				} else if (scanner.testCurrentToken("unsigned")) {
					// meaning: unsigned
					scanner.consumeCurrentToken();
					this.unsigned = true;
				} else if (scanner.testCurrentToken("COMMENT")) {
					// meaning: comment
					scanner.consumeCurrentToken();
					this.comment = scanner.getTokenBetween("'");
				} else if (scanner.testCurrentToken("on")) {
					// meaning: on update current_timestamp
					scanner.consumeCurrentToken();
					scanner.assertTokens("update", "CURRENT_TIMESTAMP");
					if (scanner.testCurrentToken("(")) {
						scanner.consumeCurrentToken();
						scanner.consumeCurrentToken();
					}
					this.onUpdateNow = true;
				} else if (scanner.testCurrentToken("character")) {
					scanner.consumeCurrentToken();
					scanner.assertToken("set");
					this.characterSet = scanner.consumeCurrentToken();
				} else if (scanner.testCurrentToken("collate")) {
					scanner.consumeCurrentToken();
					this.collation = scanner.consumeCurrentToken();
				} else if (scanner.testCurrentToken("zerofill")) {
					// ignore zero-fill
					scanner.consumeCurrentToken();
				} else {
					// meaning: unexpected
					String parsedTokens = Imploder.implode(scanner.getScannedTokens(), ",");
					throw new SofticarException("Unexpected token '%s'. Parsed tokens: %s.", scanner.consumeCurrentToken(), parsedTokens);
				}
			}
		}
	}

	public class KeyRow {

		private boolean primary = false;
		private boolean unique = false;
		private String keyName;
		private final List<String> keyColumns = new ArrayList<>();

		public boolean isPrimary() {

			return primary;
		}

		public boolean isUnique() {

			return unique;
		}

		public String getKeyName() {

			return keyName;
		}

		public List<String> getKeyColumns() {

			return keyColumns;
		}

		public DbKeyType getKeyType() {

			return primary? DbKeyType.PRIMARY : unique? DbKeyType.UNIQUE : DbKeyType.INDEX;
		}

		public KeyRow() {

			if (scanner.testCurrentToken("PRIMARY")) {
				// meaning: primary key
				scanner.assertTokens("PRIMARY", "KEY");
				this.primary = unique = true;
			} else if (scanner.testCurrentToken("UNIQUE")) {
				// meaning: unique key
				scanner.assertTokens("UNIQUE", "KEY");
				this.unique = true;
			} else if (scanner.testCurrentToken("FULLTEXT")) {
				// meaning: full text key
				scanner.assertTokens("FULLTEXT", "KEY");
			} else {
				// meaning: regular key
				scanner.assertToken("KEY");
			}

			// get key name if not primary key
			if (!primary) {
				this.keyName = scanner.getTokenBetween("`");
			}

			// optional USING attributes
			skipOptionalUsing();

			// get columns
			scanner.assertToken("(");
			while (true) {
				this.keyColumns.add(scanner.getTokenBetween("`"));

				// additional attribute?
				if (scanner.testCurrentToken("(")) {
					while (!scanner.testCurrentToken(")")) {
						scanner.consumeCurrentToken();
					}
					scanner.assertToken(")");
				}

				if (scanner.testCurrentToken(")")) {
					break;// end of key
				}

				scanner.assertToken(",");
			}
			scanner.assertToken(")");

			// optional USING attributes
			skipOptionalUsing();
		}

		private void skipOptionalUsing() {

			if (scanner.testCurrentToken("USING")) {
				scanner.assertToken("USING");
				scanner.assertAnyToken("BTREE", "HASH");
			}
		}
	}

	public class ForeignKeyRow {

		private final String name;
		private List<String> columnList = new ArrayList<>();
		private String foreignDatabase;
		private String foreignTable;
		private List<String> foreignColumnList = new ArrayList<>();
		private DbForeignKeyAction onDeleteAction = DbForeignKeyAction.getDefault();
		private DbForeignKeyAction onUpdateAction = DbForeignKeyAction.getDefault();

		public String getName() {

			return name;
		}

		public List<String> getColumnList() {

			return columnList;
		}

		public String getForeignDatabase() {

			return foreignDatabase;
		}

		public String getForeignTable() {

			return foreignTable;
		}

		public List<String> getForeignColumnList() {

			return foreignColumnList;
		}

		public DbForeignKeyAction getOnDeleteAction() {

			return onDeleteAction;
		}

		public DbForeignKeyAction getOnUpdateAction() {

			return onUpdateAction;
		}

		public ForeignKeyRow() {

			// name
			scanner.assertToken("CONSTRAINT");
			this.name = scanner.getQuotedToken();

			// columns
			scanner.assertTokens("FOREIGN", "KEY");
			this.columnList = scanner.getListOfQuotedTokens();

			// name of referenced table
			scanner.assertToken("REFERENCES");
			String name = scanner.getQuotedToken();
			if (scanner.testCurrentToken(".")) {
				scanner.consumeCurrentToken();
				this.foreignDatabase = name;
				this.foreignTable = scanner.getQuotedToken();
			} else {
				this.foreignTable = name;
			}

			// referenced columns
			this.foreignColumnList = scanner.getListOfQuotedTokens();

			// actions
			while (scanner.testCurrentToken("ON")) {
				scanner.consumeCurrentToken();
				String trigger = scanner.consumeCurrentToken();

				// get foreign key action
				DbForeignKeyAction action;
				String token = scanner.consumeCurrentToken();
				if (token.equals(DbForeignKeyAction.RESTRICT.toString())) {
					action = DbForeignKeyAction.RESTRICT;
				} else if (token.equals(DbForeignKeyAction.CASCADE.toString())) {
					action = DbForeignKeyAction.CASCADE;
				} else if (token.equals("SET")) {
					token = scanner.assertAnyToken("NULL", "DEFAULT");
					action = token.equals("NULL")? DbForeignKeyAction.SET_NULL : DbForeignKeyAction.SET_DEFAULT;
				} else if (token.equals("NO")) {
					scanner.assertToken("ACTION");
					action = DbForeignKeyAction.NO_ACTION;
				} else {
					throw new SofticarException("Unexpected token %s.", token);
				}

				// set member
				if (trigger.equals("DELETE")) {
					this.onDeleteAction = action;
				} else if (trigger.equals("UPDATE")) {
					this.onUpdateAction = action;
				} else {
					throw new SofticarException("Constraint trigger '%s' not supported.", trigger);
				}
			}
		}
	}

	private class DefaultValue {

		private DbColumnDefaultType type = DbColumnDefaultType.NONE;
		private String value = null;

		public String getValue() {

			return value;
		}

		public DbColumnDefaultType getType() {

			return type;
		}

		public DefaultValue() {

			scanner.assertToken("default");

			if (scanner.testCurrentToken("NULL")) {
				scanner.consumeCurrentToken();
				this.type = DbColumnDefaultType.NULL;
			} else if (scanner.testCurrentToken("CURRENT_TIMESTAMP")) {
				scanner.consumeCurrentToken();
				if (scanner.testCurrentToken("(")) {
					scanner.consumeCurrentToken();
					scanner.consumeCurrentToken();
				}
				this.type = DbColumnDefaultType.CURRENT_TIMESTAMP;
			} else {
				if (scanner.testCurrentToken("'")) {
					this.value = scanner.getTokenBetween("'");
				} else {
					var sign = scanner.testCurrentToken("-")? scanner.consumeCurrentToken() : "";
					this.value = sign + scanner.consumeCurrentToken();
				}
				this.type = DbColumnDefaultType.NORMAL;
			}
		}
	}

	private class StatementHead {

		protected final String databaseName;
		protected final String tableName;

		public StatementHead() {

			scanner.assertToken("CREATE");
			scanner.assertToken("TABLE");

			String firstName = scanner.getTokenBetween("`");
			String secondName = null;
			if (scanner.testCurrentToken(".")) {
				scanner.consumeCurrentToken();
				secondName = scanner.getTokenBetween("`");
			}

			if (secondName != null) {
				this.databaseName = firstName;
				this.tableName = secondName;
			} else {
				this.databaseName = null;
				this.tableName = firstName;
			}
		}
	}

	private class StatementBody {

		protected final List<ColumnRow> columnRows = new ArrayList<>();
		protected final List<KeyRow> keyRows = new ArrayList<>();
		protected final List<ForeignKeyRow> foreignKeyRows = new ArrayList<>();

		public StatementBody() {

			scanner.assertToken("(");

			while (true) {
				if (scanner.testCurrentToken(")")) {
					break;// end of body
				} else if (scanner.testCurrentToken(",")) {
					scanner.consumeCurrentToken();// separator for next row
				} else if (scanner.testCurrentToken("`")) {
					this.columnRows.add(new ColumnRow());
				} else if (scanner.testCurrentTokenAnyOf("PRIMARY", "UNIQUE", "FULLTEXT", "KEY")) {
					this.keyRows.add(new KeyRow());
				} else if (scanner.testCurrentToken("CONSTRAINT")) {
					this.foreignKeyRows.add(new ForeignKeyRow());
				} else {
					String parsedTokens = Imploder.implode(scanner.getScannedTokens(), ",");
					throw new SofticarException("Unexpected token '%s'. Parsed tokens: %s.", scanner.consumeCurrentToken(), parsedTokens);
				}
			}

			scanner.assertToken(")");
		}
	}

	private class StatementTail {

		protected String comment = "";

		public StatementTail() {

			while (scanner.hasMoreTokens()) {
				if (scanner.testAndConsumeTokenIfMatched("COMMENT")) {
					scanner.testAndConsumeTokenIfMatched("=");
					this.comment = scanner.getTokenBetween("'");
				} else {
					scanner.consumeCurrentToken();
				}
			}
		}
	}
}
