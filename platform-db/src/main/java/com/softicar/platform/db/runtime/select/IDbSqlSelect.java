package com.softicar.platform.db.runtime.select;

import com.softicar.platform.db.core.table.IDbCoreTable;
import com.softicar.platform.db.sql.token.ISqlToken;
import java.util.List;
import java.util.Set;

public interface IDbSqlSelect {

	Set<IDbCoreTable> getTables();

	boolean isActiveColumn(String columnName);

	List<Integer> getPhysicalColumnIndexList(String columnName);

	List<ISqlToken> getTokens();

	List<Object> getParameters();

	IDbSqlClause getSelectClause();

	IDbSqlClause getFromClause();

	IDbSqlClause getWhereClause();

	IDbSqlClause getGroupByClause();

	IDbSqlClause getHavingClause();

	IDbSqlClause getOrderByClause();

	IDbSqlClause getLimitClause();
}
