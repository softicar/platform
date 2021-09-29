package com.softicar.platform.common.core.transaction;

import java.io.Closeable;

public interface ITransaction extends Closeable {

	void start();

	void commit();

	void rollback();

	@Override
	void close();

	public static ITransaction noOperation() {

		return new NoOperationTransaction();
	}
}
