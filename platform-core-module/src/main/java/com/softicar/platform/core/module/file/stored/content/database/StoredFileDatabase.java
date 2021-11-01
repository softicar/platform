package com.softicar.platform.core.module.file.stored.content.database;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.string.hexadecimal.Hexadecimal;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.chunk.AGStoredFileChunk;
import com.softicar.platform.core.module.file.stored.chunk.StoredFileChunksInputStream;
import com.softicar.platform.core.module.file.stored.chunk.StoredFileChunksOutputStream;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import com.softicar.platform.core.module.file.stored.hash.IStoredFileHash;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;

public class StoredFileDatabase implements IStoredFileDatabase {

	@Override
	public void saveFileHash(AGStoredFile storedFile, byte[] hash, long size) {

		try (DbTransaction transaction = new DbTransaction()) {
			AGStoredFileSha1 sha1 = AGStoredFileSha1.TABLE//
				.createSelect(SqlSelectLock.FOR_UPDATE)
				.where(AGStoredFileSha1.HASH.equal(hash))
				.getOne();
			if (sha1 == null) {
				sha1 = new AGStoredFileSha1();
				sha1.setHash(hash);
				sha1.setSize(size);
				sha1.save();
			} else {
				// verify that size is the same
				if (!sha1.getSize().equals(size)) {
					throw new SofticarDeveloperException(
						"Got same hash '%s' but different size %s and %s.",
						Hexadecimal.getHexStringLC(hash),
						sha1.getSize(),
						size);
				}
			}
			AGStoredFile realFile = AGStoredFile.TABLE.getStub(storedFile.getId());
			realFile.setSha1(sha1);
			realFile.save();
			transaction.commit();
		}
	}

	@Override
	public IStoredFileHash getFileHash(AGStoredFile storedFile) {

		return storedFile.getSha1();
	}

	@Override
	public Collection<IStoredFileHash> getUnreferencedFileHashes() {

		return Collections
			.<IStoredFileHash> unmodifiableCollection(
				AGStoredFileSha1.createSelect().joinLeftReverse(AGStoredFile.SHA_1).where(AGStoredFile.ID.isNull()).list());
	}

	@Override
	public InputStream createChunksInputStream(AGStoredFile storedFile) {

		return new StoredFileChunksInputStream(AGStoredFile.TABLE.getStub(storedFile.getId()));
	}

	@Override
	public OutputStream createChunksOutputStream(AGStoredFile storedFile) {

		return new StoredFileChunksOutputStream(storedFile);
	}

	@Override
	public int getChunkCount(AGStoredFile storedFile) {

		// @formatter:off
		return
			AGStoredFileChunk
			.createSelect()
			.where(AGStoredFileChunk.FILE.equalID(storedFile.getId()))
			.count();
		// @formatter:on
	}
}
