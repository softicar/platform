package com.softicar.platform.db.core.dbms.microsoft.sqlserver;

import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;

public class DbMicrosoftSqlServer2000Quirks implements IDbServerQuirks {

	@Override
	public boolean isRowValueExpressionSupported() {

		return false;
	}

	@Override
	public boolean isLimitAndOffsetSupported() {

		return false;
	}

	@Override
	public boolean isQuotingMandatory() {

		return false;
	}

	@Override
	public String getQuotedIdentifier(String identifier) {

		return "[" + identifier + "]";
	}

	@Override
	public String getSignedIntegerType() {

		return "INT";
	}
}
