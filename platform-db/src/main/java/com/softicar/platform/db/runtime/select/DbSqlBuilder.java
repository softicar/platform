package com.softicar.platform.db.runtime.select;

import com.softicar.platform.common.container.tuple.AbstractTuple;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.token.ISqlToken;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class DbSqlBuilder implements IDbSqlBuilder {

	private final IDbServerQuirks serverQuirks;
	private final Stack<DbSqlSelect> selectStack;
	private DbSqlSelect select;

	public DbSqlBuilder() {

		this.serverQuirks = DbConnections.getServerQuirks();
		this.selectStack = new Stack<>();
		this.select = new DbSqlSelect();
		this.selectStack.push(select);
	}

	// -------------------- sub-selects -------------------- //

	@Override
	public void startSubSelect() {

		this.select = new DbSqlSelect();
		this.selectStack.push(select);
	}

	@Override
	public void finishSubSelect() {

		if (this.selectStack.size() > 1) {
			DbSqlSelect subSelect = this.selectStack.pop();
			this.select = this.selectStack.peek();
			this.select.addSubSelect(subSelect);
		} else {
			throw new IllegalStateException("Tried to finish top-level select.");
		}
	}

	@Override
	public IDbSqlSelect getSelect() {

		return select;
	}

	// -------------------- adders -------------------- //

	@Override
	public DbSqlBuilder addToken(ISqlToken token) {

		select.addToken(token);
		return this;
	}

	@Override
	public DbSqlBuilder addParameter(Object parameter) {

		if (parameter instanceof AbstractTuple) {
			addTupleParameter((AbstractTuple) parameter);
		} else {
			select.addToken(new DbSqlRawToken("?"));
			select.addParameter(parameter);
		}
		return this;
	}

	private void addTupleParameter(AbstractTuple tuple) {

		if (tuple.isEmpty()) {
			throw new IllegalArgumentException("Tuple parameters may not be empty.");
		}

		select.addToken(SqlSymbol.LEFT_PARENTHESIS);
		addParameter(tuple.get(0));
		for (int i = 1; i < tuple.size(); ++i) {
			select.addToken(SqlSymbol.COMMA);
			addParameter(tuple.get(i));
		}
		select.addToken(SqlSymbol.RIGHT_PARENTHESIS);
	}

	@Override
	public DbSqlBuilder addParameters(Iterable<?> parameters) {

		boolean first = true;
		for (Object parameter: parameters) {
			if (first) {
				first = false;
			} else {
				addToken(SqlSymbol.COMMA);
			}
			addParameter(parameter);
		}
		return this;
	}

	@Override
	public IDbSqlBuilder addTableColumns(String tableAlias, ISqlTable<?> table, String columnName) {

		boolean first = true;
		for (ISqlField<?, ?> field: table.getAllFields()) {
			if (first) {
				first = false;
			} else {
				addToken(SqlSymbol.COMMA);
			}

			addIdentifier(tableAlias, field.getName());
			addToken(SqlKeyword.AS);
			addIdentifier(columnName + "$" + field.getName());
		}
		return this;
	}

	@Override
	public IDbSqlBuilder addSubSelectTableColumns(String subSelectAlias, ISqlTable<?> table, String tableColumnName) {

		boolean first = true;
		for (ISqlField<?, ?> field: table.getAllFields()) {
			if (first) {
				first = false;
			} else {
				addToken(SqlSymbol.COMMA);
			}

			addIdentifier(subSelectAlias, tableColumnName + "$" + field.getName());
		}
		return this;
	}

	@Override
	public DbSqlBuilder addIdentifier(String...identifierParts) {

		return addIdentifier(Arrays.asList(identifierParts));
	}

	@Override
	public DbSqlBuilder addIdentifier(List<String> identifierParts) {

		boolean first = true;
		for (String identifierPart: identifierParts) {
			if (first) {
				first = false;
			} else {
				addToken(SqlSymbol.DOT);
			}

			addToken(new DbSqlIdentifier(serverQuirks, identifierPart));
		}
		return this;
	}

	@Override
	public IDbSqlBuilder addIdentifier(ISqlTable<?> table) {

		select.addTable(table);
		DbTableName tableName = table.getFullName();
		return addIdentifier(tableName.getDatabaseName(), tableName.getSimpleName());
	}

	@Override
	public IDbSqlBuilder addIdentifier(String alias, ISqlField<?, ?> field) {

		addIdentifier(alias, field.getName());
		return this;
	}

	@Override
	public IDbSqlBuilder addLiteral(boolean literal) {

		return addToken(new DbSqlRawToken(literal? "TRUE" : "FALSE"));
	}

	@Override
	public IDbSqlBuilder addLiteral(int literal) {

		return addToken(new DbSqlRawToken(literal + ""));
	}

	@Override
	public IDbSqlBuilder addLiteral(long literal) {

		return addToken(new DbSqlRawToken(literal + ""));
	}

	@Override
	public IDbSqlBuilder addLiteral(BigDecimal literal) {

		return addToken(new DbSqlRawToken(literal.toString()));
	}

	@Override
	public IDbSqlBuilder addLiteral(String literal) {

		return addToken(new DbSqlRawToken("'" + literal + "'"));
	}

	// -------------------- clauses -------------------- //

	@Override
	public IDbSqlBuilder SELECT() {

		select.SELECT();
		return this;
	}

	@Override
	public IDbSqlBuilder SELECT(IDbQueryColumn<?, ?> column) {

		select.SELECT(column);
		return this;
	}

	@Override
	public IDbSqlBuilder FROM() {

		select.FROM();
		return this;
	}

	@Override
	public IDbSqlBuilder JOIN(SqlKeyword joinType) {

		select.JOIN(joinType);
		return this;
	}

	@Override
	public IDbSqlBuilder ON() {

		select.ON();
		return this;
	}

	@Override
	public IDbSqlBuilder WHERE() {

		select.WHERE();
		return this;
	}

	@Override
	public IDbSqlBuilder GROUP_BY() {

		select.GROUP_BY();
		return this;
	}

	@Override
	public IDbSqlBuilder HAVING() {

		select.HAVING();
		return this;
	}

	@Override
	public IDbSqlBuilder ORDER_BY() {

		select.ORDER_BY();
		return this;
	}

	@Override
	public IDbSqlBuilder LIMIT() {

		select.LIMIT();
		return this;
	}

	// -------------------- miscellaneous -------------------- //

	@Override
	public IDbSqlBuilder setStraightJoin(boolean straightJoin) {

		selectStack.firstElement().setStraightJoin(straightJoin);
		return this;
	}
}
