package com.softicar.platform.common.core.transaction;

public class NoOperationTransaction implements ITransaction {

	@Override
	public void start() {

		// does nothing by design
	}

	@Override
	public void commit() {

		// does nothing by design
	}

	@Override
	public void rollback() {

		// does nothing by design
	}

	@Override
	public void close() {

		// does nothing by design
	}
}
