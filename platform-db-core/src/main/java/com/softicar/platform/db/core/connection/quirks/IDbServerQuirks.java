package com.softicar.platform.db.core.connection.quirks;

public interface IDbServerQuirks {

	boolean isRowValueExpressionSupported();

	boolean isLimitAndOffsetSupported();

	boolean isQuotingMandatory();

	String getQuotedIdentifier(String identifier);

	String getSignedIntegerType();
}
