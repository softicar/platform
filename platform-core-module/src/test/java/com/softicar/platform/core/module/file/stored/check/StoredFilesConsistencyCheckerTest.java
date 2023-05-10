package com.softicar.platform.core.module.file.stored.check;

import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import java.io.ByteArrayInputStream;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;

public class StoredFilesConsistencyCheckerTest extends AbstractDbTest implements CoreModuleTestFixtureMethods {

	private static final byte[] CONTENT_A = "Hello".getBytes(Charsets.UTF8);
	private static final byte[] CONTENT_B = "World".getBytes(Charsets.UTF8);
	private static final String CONTENT_A_PATH = "/F7/FF9E8B7BB2E09B70935A5D785E0CC5D9D0ABF0";

	private final IStoredFileContentStore storeA;
	private final IStoredFileContentStore storeB;
	private final IStoredFileContentStore storeC;
	private final StoredFilesConsistencyChecker checker;

	public StoredFilesConsistencyCheckerTest() {

		this.storeA = Mockito.mock(IStoredFileContentStore.class);
		this.storeB = Mockito.mock(IStoredFileContentStore.class);
		this.storeC = Mockito.mock(IStoredFileContentStore.class);
		this.checker = new StoredFilesConsistencyChecker(List.of(storeA, storeB, storeC));

		Mockito.when(storeA.getLocation()).thenReturn("A");
		Mockito.when(storeB.getLocation()).thenReturn("B");
		Mockito.when(storeC.getLocation()).thenReturn("C");
	}

	@Test
	public void testWithFileOnNoContentStore() {

		insertSha1(CONTENT_A);

		assertExceptionMessageLines(() -> checker.checkAll())//
			.assertLine("no content store for '%s'".formatted(CONTENT_A_PATH));
	}

	@Test
	@SuppressWarnings("resource")
	public void testWithFileOnOneContentStore() {

		insertSha1(CONTENT_A);
		Mockito.when(storeB.exists(CONTENT_A_PATH)).thenReturn(true);
		Mockito.when(storeB.getFileInputStream(CONTENT_A_PATH)).thenAnswer(dummy -> new ByteArrayInputStream(CONTENT_A));

		checker.checkAll();
	}

	@Test
	@SuppressWarnings("resource")
	public void testWithCorruptFileOnContentStore() {

		insertSha1(CONTENT_A);
		Mockito.when(storeB.exists(CONTENT_A_PATH)).thenReturn(true);
		Mockito.when(storeB.getFileInputStream(CONTENT_A_PATH)).thenAnswer(dummy -> new ByteArrayInputStream(CONTENT_B));

		assertExceptionMessageLines(() -> checker.checkAll())//
			.assertLine("corrupted file '%s' on store 'B':".formatted(CONTENT_A_PATH));
	}

	@Test
	@SuppressWarnings("resource")
	public void testWithCorruptedAndNonCurruptedFileOnContentStores() {

		insertSha1(CONTENT_A);
		Mockito.when(storeA.exists(CONTENT_A_PATH)).thenReturn(true);
		Mockito.when(storeA.getFileInputStream(CONTENT_A_PATH)).thenAnswer(dummy -> new ByteArrayInputStream(CONTENT_B));
		Mockito.when(storeB.exists(CONTENT_A_PATH)).thenReturn(true);
		Mockito.when(storeB.getFileInputStream(CONTENT_A_PATH)).thenAnswer(dummy -> new ByteArrayInputStream(CONTENT_A));
		Mockito.when(storeC.exists(CONTENT_A_PATH)).thenReturn(true);
		Mockito.when(storeC.getFileInputStream(CONTENT_A_PATH)).thenAnswer(dummy -> new ByteArrayInputStream(CONTENT_B));

		assertExceptionMessageLines(() -> checker.checkAll())//
			.assertLine("corrupted file '%s' on store 'A':".formatted(CONTENT_A_PATH))
			.assertLine("corrupted file '%s' on store 'C':".formatted(CONTENT_A_PATH));
	}

	private AGStoredFileSha1 insertSha1(byte[] content) {

		return new AGStoredFileSha1()//
			.setSize((long) content.length)
			.setHash(Hash.SHA1.getHash(content))
			.save();
	}
}
