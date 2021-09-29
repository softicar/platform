package com.softicar.platform.core.module.file.stored.content;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.string.hexadecimal.Hexadecimal;
import com.softicar.platform.common.string.unicode.Utf8Convering;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.content.database.IStoredFileDatabase;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;
import com.softicar.platform.core.module.log.configuration.CurrentLogDbConfiguration;
import com.softicar.platform.core.module.log.message.AGLogMessage;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.db.runtime.utils.DbAssertUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class StoredFileContentUploaderTest extends AbstractDbTest implements CoreModuleTestFixtureMethods {

	private static final int FILE_ID = 1337;
	private static final String TMP_FILENAME = "/tmp/" + FILE_ID;
	private static final String HASH_FILENAME = "/76/05069FBF57D1C591D88D06D7FC4A30B0B5CBA7";
	private static final String CONTENT_STRING = "hello you there";
	private static final String CONTENT_HASH_STRING = "7605069FBF57D1C591D88D06D7FC4A30B0B5CBA7";

	private final AGUser user;
	private final ByteArrayOutputStream output;
	private final IStoredFileDatabase database;
	private final IStoredFileContentStore store;
	private final AGStoredFile storedFile;
	private final StoredFileContentUploader uploader;

	public StoredFileContentUploaderTest() {

		this.user = insertTestUser();
		this.output = new ByteArrayOutputStream();
		this.database = Mockito.mock(IStoredFileDatabase.class);
		this.store = Mockito.mock(IStoredFileContentStore.class);
		this.storedFile = insertStoredFile(FILE_ID);
		this.uploader = new StoredFileContentUploader(database, store, storedFile);

		setupMocking();
	}

	@SuppressWarnings("resource")
	private void setupMocking() {

		Mockito.when(store.isEnabled()).thenReturn(true);
		Mockito.when(store.isAvailable()).thenReturn(true);
		Mockito.when(store.getFreeDiskSpace()).thenReturn(Long.MAX_VALUE);
		Mockito.when(store.createFile(ArgumentMatchers.anyString())).thenReturn(output);
		Mockito.when(database.createChunksOutputStream(storedFile)).thenReturn(output);
	}

	@Test
	public void writesCorrectContent() {

		upload();

		assertArrayEquals(Utf8Convering.toUtf8(CONTENT_STRING), output.toByteArray());
	}

	@Test
	public void savesCorrectHashAndSize() {

		upload();

		byte[] hash = Hexadecimal.getBytesFromHexString(CONTENT_HASH_STRING);
		long length = CONTENT_STRING.length();
		Mockito.verify(database).saveFileHash(ArgumentMatchers.same(storedFile), ArgumentMatchers.eq(hash), ArgumentMatchers.eq(length));
	}

	@Test
	public void savesHashBeforeMovingFile() {

		Mockito.when(store.exists(HASH_FILENAME)).thenReturn(false);

		upload();

		InOrder inOrder = Mockito.inOrder(database, store);
		inOrder.verify(database).saveFileHash(ArgumentMatchers.any(AGStoredFile.class), ArgumentMatchers.any(byte[].class), ArgumentMatchers.anyLong());
		inOrder.verify(store).moveFile(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
	}

	@Test
	public void savesHashBeforeRemovingFile() {

		Mockito.when(store.exists(HASH_FILENAME)).thenReturn(true);

		upload();

		InOrder inOrder = Mockito.inOrder(database, store);
		inOrder.verify(database).saveFileHash(ArgumentMatchers.any(AGStoredFile.class), ArgumentMatchers.any(byte[].class), ArgumentMatchers.anyLong());
		inOrder.verify(store).removeFile(ArgumentMatchers.anyString());
	}

	@Test
	public void createsTemporaryFolder() {

		upload();

		Mockito.verify(store).createFolderIfDoesNotExist("/tmp");
	}

	@Test
	public void createsPrefixFolder() {

		upload();

		Mockito.verify(store).createFolderIfDoesNotExist("/76");
	}

	@Test
	public void movesAndRenamesAccordingToHash() {

		upload();

		Mockito.verify(store).moveFile(TMP_FILENAME, HASH_FILENAME);
	}

	@SuppressWarnings("resource")
	@Test
	public void usesDatabaseOutputStreamIfFileStoreNotAvailable() {

		setupPanicLogging();
		Mockito.when(store.isAvailable()).thenReturn(false);

		upload();

		Mockito.verify(database).createChunksOutputStream(ArgumentMatchers.same(storedFile));
		DbAssertUtils.assertOne(AGLogMessage.TABLE);
	}

	@SuppressWarnings("resource")
	@Test
	public void usesDatabaseOutputStreamIfFileStoreDisabled() {

		Mockito.when(store.isEnabled()).thenReturn(false);

		upload();

		Mockito.verify(database).createChunksOutputStream(ArgumentMatchers.same(storedFile));
	}

	@Test
	public void doesNotSendPanicIfFileStoreNotEnabled() {

		setupPanicLogging();
		Mockito.when(store.isEnabled()).thenReturn(false);

		upload();

		DbAssertUtils.assertNone(AGLogMessage.TABLE);
	}

	@Test
	public void sendsPanicIfFileStoreNotAvailable() {

		setupPanicLogging();
		Mockito.when(store.isAvailable()).thenReturn(false);

		upload();

		DbAssertUtils.assertOne(AGLogMessage.TABLE);
	}

	@Test
	public void sendsPanicIfFileStoreHasNotEnoughSpace() {

		setupPanicLogging();
		Mockito.when(store.getFreeDiskSpace()).thenReturn(0L);

		upload();

		DbAssertUtils.assertOne(AGLogMessage.TABLE);
	}

	@Test
	public void doesNotSaveHashIfFileStoreNotAvailable() {

		setupPanicLogging();
		Mockito.when(store.isAvailable()).thenReturn(false);

		upload();

		Mockito
			.verify(database, Mockito.never())
			.saveFileHash(ArgumentMatchers.any(AGStoredFile.class), ArgumentMatchers.any(byte[].class), ArgumentMatchers.anyLong());
		DbAssertUtils.assertOne(AGLogMessage.TABLE);
	}

	@Test
	public void doesNotMoveButRemovesFileIfHashAlreadyExists() {

		Mockito.when(store.exists(HASH_FILENAME)).thenReturn(true);

		upload();

		Mockito.verify(store).removeFile(TMP_FILENAME);
		Mockito.verify(store, Mockito.never()).moveFile(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
	}

	@Test(expected = SofticarDeveloperException.class)
	public void throwsIfEqualHashButDifferentSize() {

		Mockito.when(store.exists(HASH_FILENAME)).thenReturn(true);
		Mockito.when(store.getFileSize(HASH_FILENAME)).thenReturn(42L);
		Mockito.when(store.getFileSize(TMP_FILENAME)).thenReturn(33L);

		upload();
	}

	private AGStoredFile insertStoredFile(int fileId) {

		AGStoredFile.TABLE//
			.createInsert()
			.set(AGStoredFile.ID, fileId)
			.set(AGStoredFile.FILE_NAME, "fileName")
			.set(AGStoredFile.CREATED_BY, user)
			.executeWithoutIdGeneration();
		return AGStoredFile.TABLE.getStub(fileId);
	}

	private static void setupPanicLogging() {

		CurrentLogDbConfiguration.set(() -> true);
	}

	private void upload() {

		try (OutputStream stream = uploader.createOutputStream()) {
			stream.write(Utf8Convering.toUtf8(CONTENT_STRING));
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
