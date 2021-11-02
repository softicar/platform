package com.softicar.platform.core.module.file.stored.hash;

import com.softicar.platform.db.core.transaction.IDbTransaction;

public interface IStoredFileHash {

	byte[] getHash();

	String getHashString();

	IDbTransaction lock();

	boolean isReferenced();

	IStoredFileHash delete();
}
