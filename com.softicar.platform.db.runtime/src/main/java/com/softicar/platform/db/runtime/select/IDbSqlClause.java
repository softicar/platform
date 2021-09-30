package com.softicar.platform.db.runtime.select;

import com.softicar.platform.db.sql.token.ISqlToken;
import java.util.List;

public interface IDbSqlClause {

	List<ISqlToken> getTokens();

	List<Object> getParameters();

	void clear();
}
