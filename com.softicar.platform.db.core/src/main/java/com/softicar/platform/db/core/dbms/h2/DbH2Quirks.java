package com.softicar.platform.db.core.dbms.h2;

import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;

public class DbH2Quirks implements IDbServerQuirks {

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

		return true;
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
