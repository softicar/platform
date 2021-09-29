package com.softicar.platform.db.core.transaction;

import java.util.Optional;

public interface IDbTransactionHierarchy {

	Optional<IDbTransaction> getRootTransaction();

	Optional<IDbTransaction> getCurrentTransaction();

	Optional<IDbTransaction> getChildTransaction(IDbTransaction transaction);

	int registerTransaction(IDbTransaction transaction);

	void unregisterTransaction(IDbTransaction dbTransaction);
}
