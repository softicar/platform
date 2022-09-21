package com.softicar.platform.db.sql.statement.builder.clause;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.builder.SqlDeleteBuilder;
import com.softicar.platform.db.sql.statement.builder.SqlSelectBuilder;
import com.softicar.platform.db.sql.statement.builder.SqlStatementBuilder;
import java.util.HashSet;
import java.util.Set;

/**
 * Head clause of an SQL statement used in {@link SqlStatementBuilder}.
 * <p>
 * TODO This class should be renamed to SqlFromClauseBuilder and only used by
 * {@link SqlSelectBuilder}. For this, other builders, e.g.
 * {@link SqlDeleteBuilder}, need to be adapted.
 *
 * @author Oliver Richers
 */
public class SqlHeadClauseBuilder extends SqlClauseBuilder {

	private final Set<ISqlTable<?>> tables;
	private int aliasCounter;

	public SqlHeadClauseBuilder(SqlStatementBuilder statementBuilder, ISqlTable<?> table) {

		super(statementBuilder);

		this.tables = new HashSet<>();

		tables.add(table);
	}

	public Set<ISqlTable<?>> getTables() {

		return tables;
	}

	public String allocateJoinAlias(ISqlTable<?> table) {

		tables.add(table);
		return "a" + (++aliasCounter);
	}
}
