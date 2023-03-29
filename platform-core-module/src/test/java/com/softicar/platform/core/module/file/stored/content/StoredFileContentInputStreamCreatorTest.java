package com.softicar.platform.core.module.file.stored.content;

import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.common.string.unicode.Utf8Convering;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.chunk.AGStoredFileChunk;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import com.softicar.platform.core.module.log.configuration.CurrentLogDbConfiguration;
import com.softicar.platform.core.module.log.message.AGLogMessage;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.db.runtime.utils.DbAssertUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.mockito.Mockito;

public class StoredFileContentInputStreamCreatorTest extends AbstractDbTest implements CoreModuleTestFixtureMethods {

	private static final byte[] TEST_CONTENT = Utf8Convering.toUtf8("this is the file content");
	private static final byte[] EMPTY_CONTENT = new byte[0];
	private final AGUser user;
	private final StoredFileTestStore contentStore;
	private final AGStoredFile storedFile;
	private final StoredFileContentInputStreamCreator streamCreator;

	public StoredFileContentInputStreamCreatorTest() {

		this.user = insertTestUser();
		this.contentStore = new StoredFileTestStore();
		this.storedFile = new AGStoredFile()//
			.setFileName("some-filename")
			.setCreatedBy(user)
			.save();
		this.streamCreator = new StoredFileContentInputStreamCreator(storedFile)//
			.addContentStore(contentStore);
	}

	@Test
	public void testWithSha1() throws IOException {

		// insert sha1 and content
		insertSha1(storedFile, TEST_CONTENT);
		contentStore.addFileContent(TEST_CONTENT);

		try (InputStream stream = streamCreator.create()) {
			assertContent(TEST_CONTENT, stream);
		}
		assertArrayEquals(TEST_CONTENT, streamCreator.getBytes());
	}

	@Test
	public void testWithSha1AndEmptyContent() throws IOException {

		// insert sha1 and content
		insertSha1(storedFile, EMPTY_CONTENT);
		contentStore.addFileContent(EMPTY_CONTENT);

		try (InputStream stream = streamCreator.create()) {
			assertContent(EMPTY_CONTENT, stream);
		}
		assertArrayEquals(EMPTY_CONTENT, streamCreator.getBytes());
	}

	@Test
	public void testWithoutSha1ButWithChunks() throws IOException {

		// insert chunks with content
		new AGStoredFileChunk()//
			.setFile(storedFile)
			.setChunkIndex(0)
			.setChunkData(TEST_CONTENT)
			.setChunkSize(TEST_CONTENT.length)
			.save();

		try (InputStream stream = streamCreator.create()) {
			assertContent(TEST_CONTENT, stream);
		}
	}

	@Test(expected = StoredFileContentNotFoundException.class)
	public void testCreateWithoutSha1AndWithoutChunks() throws IOException {

		streamCreator.create().close();
	}

	@Test(expected = StoredFileContentNotFoundException.class)
	public void testGetBytesWithoutSha1AndWithoutChunks() {

		streamCreator.getBytes();
	}

	@Test
	public void testWithSha1AndBrokenContentStore() {

		// setup panic logging
		setupPanicLogging();

		// insert sha1
		insertSha1(storedFile, TEST_CONTENT);

		// assert that file not found is thrown
		Asserts
			.assertException(//
				StoredFileContentNotFoundException.class,
				() -> new StoredFileContentInputStreamCreator(storedFile)//
					.addContentStore(createBrokenStore())
					.create());

		// assert that a panic message was inserted
		DbAssertUtils.assertOne(AGLogMessage.TABLE);
	}

	@Test
	public void testWithSha1AndBrokenContentAndGoodStore() throws IOException {

		// setup panic logging
		setupPanicLogging();

		// insert sha1 and content
		insertSha1(storedFile, TEST_CONTENT);
		contentStore.addFileContent(TEST_CONTENT);

		// assert correct file content
		try (InputStream stream = new StoredFileContentInputStreamCreator(storedFile)//
			.addContentStore(createBrokenStore())
			.addContentStore(contentStore)
			.create()) {
			assertContent(TEST_CONTENT, stream);
		}

		// assert that no panic message was inserted
		DbAssertUtils.assertCount(0, AGLogMessage.TABLE);
	}

	// ---------- private ---------- //

	private static void insertSha1(AGStoredFile file, byte[] data) {

		AGStoredFileSha1 sha1 = new AGStoredFileSha1()//
			.setHash(Hash.SHA1.createDigest().digest(data))
			.setSize((long) data.length)
			.save();
		file//
			.setSha1(sha1)
			.save();
	}

	private static void setupPanicLogging() {

		CurrentLogDbConfiguration.set(() -> true);
	}

	@SuppressWarnings("resource")
	private static IStoredFileContentStore createBrokenStore() {

		IStoredFileContentStore brokenStore = Mockito.mock(IStoredFileContentStore.class);
		Mockito.when(brokenStore.isAccessible()).thenReturn(true);
		Mockito.when(brokenStore.exists(Mockito.any())).thenThrow(new RuntimeException());
		Mockito.when(brokenStore.getFileInputStream(Mockito.any())).thenThrow(new RuntimeException());
		return brokenStore;
	}

	private static void assertContent(byte[] expectedContent, InputStream inputStream) {

		assertArrayEquals(expectedContent, readInputStream(inputStream));
	}

	private static byte[] readInputStream(InputStream inputStream) {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		StreamUtils.copy(inputStream, buffer);
		return buffer.toByteArray();
	}
}
