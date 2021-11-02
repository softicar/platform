package com.softicar.platform.db.core.dbms.ibm.as400;

import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;

public class DbIbmAs400Quirks implements IDbServerQuirks {

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

		return "\"" + identifier + "\"";
	}

	@Override
	public String getSignedIntegerType() {

		return "INT";
	}
}
