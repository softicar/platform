package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.core.module.file.stored.content.StoredFileContentName;
import com.softicar.platform.core.module.file.stored.content.database.IStoredFileDatabase;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;
import com.softicar.platform.core.module.file.stored.hash.IStoredFileHash;
import com.softicar.platform.db.core.transaction.IDbTransaction;
import java.util.Arrays;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class StoredFileStoreGarbageCollectorTest {

	private static final byte[] CONTENT_HASH = Hash.SHA1.getHash("foo");
	private static final String CONTENT_FILENAME = new StoredFileContentName(CONTENT_HASH).getFullFilename();
	private final IStoredFileDatabase database;
	private final IStoredFileContentStore store;
	private final IStoredFileHash hash;
	private final IDbTransaction transaction;

	public StoredFileStoreGarbageCollectorTest() {

		LogLevel.ERROR.set();

		this.database = Mockito.mock(IStoredFileDatabase.class);
		this.store = Mockito.mock(IStoredFileContentStore.class);
		this.hash = Mockito.mock(IStoredFileHash.class);
		this.transaction = Mockito.mock(IDbTransaction.class);

		setupMocking();
	}

	@SuppressWarnings("resource")
	private void setupMocking() {

		Mockito.when(hash.getHash()).thenReturn(CONTENT_HASH);
		Mockito.when(hash.lock()).thenReturn(transaction);
	}

	@Test
	public void removesFileIfHashNotReferenced() {

		Mockito.when(hash.isReferenced()).thenReturn(false);
		Mockito.when(database.getUnreferencedFileHashes()).thenReturn(Arrays.asList(hash));
		Mockito.when(store.exists(CONTENT_FILENAME)).thenReturn(true);

		new StoredFileStoreGarbageCollector(database, store).collect();

		Mockito.verify(store).removeFile(CONTENT_FILENAME);
	}

	@Test
	public void doesNotRemoveNonExistingFileEvenIfHashNotReferenced() {

		Mockito.when(hash.isReferenced()).thenReturn(false);
		Mockito.when(database.getUnreferencedFileHashes()).thenReturn(Arrays.asList(hash));
		Mockito.when(store.exists(CONTENT_FILENAME)).thenReturn(false);

		new StoredFileStoreGarbageCollector(database, store).collect();

		Mockito.verify(store, Mockito.never()).removeFile(ArgumentMatchers.anyString());
	}

	@Test
	public void removesHashIfHashNotReferenced() {

		Mockito.when(hash.isReferenced()).thenReturn(false);
		Mockito.when(database.getUnreferencedFileHashes()).thenReturn(Arrays.asList(hash));

		new StoredFileStoreGarbageCollector(database, store).collect();

		Mockito.verify(hash).delete();
	}

	@SuppressWarnings("resource")
	@Test
	public void locksHashBeforeCheckIfHashIsReferenced() {

		Mockito.when(database.getUnreferencedFileHashes()).thenReturn(Arrays.asList(hash));

		new StoredFileStoreGarbageCollector(database, store).collect();

		InOrder inOrder = Mockito.inOrder(hash);
		inOrder.verify(hash).lock();
		inOrder.verify(hash).isReferenced();
	}

	@SuppressWarnings("resource")
	@Test
	public void locksHashRemovesFileAndCommitsTransactionCorrectInOrder() {

		Mockito.when(database.getUnreferencedFileHashes()).thenReturn(Arrays.asList(hash));
		Mockito.when(store.exists(CONTENT_FILENAME)).thenReturn(true);

		new StoredFileStoreGarbageCollector(database, store).collect();

		InOrder inOrder = Mockito.inOrder(hash, store, transaction);
		inOrder.verify(hash).lock();
		inOrder.verify(store).removeFile(CONTENT_FILENAME);
		inOrder.verify(transaction).commit();
	}

	@Test
	public void doesNotRemoveFileOrHashIfFileIsStillReferenced() {

		Mockito.when(hash.isReferenced()).thenReturn(true);
		Mockito.when(database.getUnreferencedFileHashes()).thenReturn(Arrays.asList(hash));

		new StoredFileStoreGarbageCollector(database, store).collect();

		Mockito.verify(store, Mockito.never()).removeFile(ArgumentMatchers.anyString());
		Mockito.verify(hash, Mockito.never()).delete();
	}
}
