package com.softicar.platform.core.module.file.stored.cleanup;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.string.unicode.Utf8Convering;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.chunk.AGStoredFileChunk;
import com.softicar.platform.core.module.file.stored.content.database.StoredFileDatabase;
import com.softicar.platform.core.module.file.stored.content.store.StoredFileInMemoryContentStore;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class StoredFileChunksToFileStoreMigratorTest extends AbstractDbTest {

	private static final String EXPECTED_HASH = "0A0A9F2A6772942557AB5355D76AF442F8F65E01";
	private static final String EXPECTED_FILENAME = "/" + EXPECTED_HASH.substring(0, 2) + "/" + EXPECTED_HASH.substring(2);

	public StoredFileChunksToFileStoreMigratorTest() {

		var file = new AGStoredFile()//
			.setFileName("Foo")
			.setContentType(MimeType.TEXT_PLAIN.getIdentifier())
			.setCreatedBy(AGUser.getSystemUser())
			.save();
		inputChunk(file, Utf8Convering.toUtf8("Hello, "));
		inputChunk(file, Utf8Convering.toUtf8("World!"));
	}

	@Test
	public void testMigrateAll() {

		var store = new StoredFileInMemoryContentStore();

		StoredFileChunksToFileStoreMigrator.migrateAll(new StoredFileDatabase(), store);

		assertEquals("[" + EXPECTED_FILENAME + "]", store.getAllFilePaths().toString());
		assertEmpty(AGStoredFileChunk.TABLE.loadAll());
		assertOne(AGStoredFileSha1.TABLE.loadAll());
		assertEquals("Hello, World!", Utf8Convering.fromUtf8(store.getFileContent(EXPECTED_FILENAME)));
	}

	private AGStoredFileChunk inputChunk(AGStoredFile file, byte[] data) {

		return new AGStoredFileChunk()//
			.setFile(file)
			.setChunkIndex(AGStoredFileChunk.TABLE.countAll())
			.setChunkData(data)
			.setChunkSize(data.length)
			.save();
	}
}
