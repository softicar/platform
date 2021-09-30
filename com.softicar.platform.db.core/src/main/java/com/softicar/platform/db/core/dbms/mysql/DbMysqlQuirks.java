package com.softicar.platform.db.core.dbms.mysql;

import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;

public class DbMysqlQuirks implements IDbServerQuirks {

	@Override
	public boolean isRowValueExpressionSupported() {

		return true;
	}

	@Override
	public boolean isLimitAndOffsetSupported() {

		return true;
	}

	@Override
	public boolean isQuotingMandatory() {

		return false;
	}

	@Override
	public String getQuotedIdentifier(String identifier) {

		return "`" + identifier + "`";
	}

	@Override
	public String getSignedIntegerType() {

		return "SIGNED";
	}
}
