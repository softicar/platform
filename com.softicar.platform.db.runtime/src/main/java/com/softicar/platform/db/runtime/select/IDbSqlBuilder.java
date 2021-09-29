package com.softicar.platform.db.runtime.select;

import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.token.ISqlToken;
import com.softicar.platform.db.sql.token.SqlKeyword;
import java.math.BigDecimal;
import java.util.List;

public interface IDbSqlBuilder {

	// -------------------- sub-selects -------------------- //

	void startSubSelect();

	void finishSubSelect();

	IDbSqlSelect getSelect();

	// -------------------- adders -------------------- //

	IDbSqlBuilder addToken(ISqlToken token);

	IDbSqlBuilder addIdentifier(String...identifierParts);

	IDbSqlBuilder addIdentifier(List<String> identifierParts);

	IDbSqlBuilder addIdentifier(ISqlTable<?> table);

	IDbSqlBuilder addIdentifier(String alias, ISqlField<?, ?> field);

	IDbSqlBuilder addParameter(Object parameter);

	IDbSqlBuilder addParameters(Iterable<?> parameters);

	IDbSqlBuilder addTableColumns(String tableAlias, ISqlTable<?> table, String columnName);

	IDbSqlBuilder addSubSelectTableColumns(String subSelectAlias, ISqlTable<?> table, String tableColumnName);

	IDbSqlBuilder addLiteral(boolean literal);

	IDbSqlBuilder addLiteral(int literal);

	IDbSqlBuilder addLiteral(long literal);

	IDbSqlBuilder addLiteral(BigDecimal literal);

	IDbSqlBuilder addLiteral(String literal);

	// -------------------- clauses -------------------- //

	IDbSqlBuilder SELECT();

	IDbSqlBuilder SELECT(IDbQueryColumn<?, ?> column);

	IDbSqlBuilder FROM();

	IDbSqlBuilder JOIN(SqlKeyword joinType);

	IDbSqlBuilder ON();

	IDbSqlBuilder WHERE();

	IDbSqlBuilder GROUP_BY();

	IDbSqlBuilder HAVING();

	IDbSqlBuilder ORDER_BY();

	IDbSqlBuilder LIMIT();

	// -------------------- miscellaneous -------------------- //

	IDbSqlBuilder setStraightJoin(boolean straightJoin);
}
