package com.softicar.platform.core.module.file.stored.hash;

import com.softicar.platform.common.string.hexadecimal.Hexadecimal;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.hash.IStoredFileSha1Query.IRow;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.core.transaction.IDbTransaction;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class AGStoredFileSha1 extends AGStoredFileSha1Generated implements IStoredFileHash {

	@Override
	public IDbTransaction lock() {

		IDbTransaction transaction = new DbTransaction();
		reloadForUpdate();
		return transaction;
	}

	@Override
	public boolean isReferenced() {

		return AGStoredFile.createSelect().where(AGStoredFile.SHA_1.equal(this)).count() > 0;
	}

	@Override
	public String getHashString() {

		byte[] hashBytes = getHash();
		if (hashBytes != null) {
			return Hexadecimal.getHexStringUC(hashBytes);
		} else {
			return null;
		}
	}

	public static Map<AGStoredFile, AGStoredFileSha1> loadAll(Collection<AGStoredFile> files) {

		Map<AGStoredFile, AGStoredFileSha1> map = new TreeMap<>();
		for (IRow row: IStoredFileSha1Query.FACTORY.createQuery().setFiles(files)) {
			map.put(row.getFile(), row.getSha1());
		}
		return map;
	}
}
