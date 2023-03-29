package com.softicar.platform.core.module.file.stored.content.store;

import com.softicar.platform.core.module.file.stored.repository.AGStoredFileRepository;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import java.util.Optional;
import org.junit.Test;

public class StoredFileContentStoreFactoryTest extends AbstractCoreTest {

	private final StoredFileContentStoreFactory factory;

	public StoredFileContentStoreFactoryTest() {

		this.factory = new StoredFileContentStoreFactory();
	}

	@Test
	public void testWithFileUrlAbsolute() {

		var repository = insertRepository("file:///dir/subdir");
		Optional<IStoredFileContentStore> store = factory.create(repository);
		assertTrue(store.isPresent());
		assertTrue(StoredFileFileSystemContentStore.class.isInstance(store.get()));
	}

	@Test
	public void testWithFileUrlAbsoluteShort() {

		var repository = insertRepository("file:/dir/subdir");
		Optional<IStoredFileContentStore> store = factory.create(repository);
		assertTrue(store.isPresent());
		assertTrue(StoredFileFileSystemContentStore.class.isInstance(store.get()));
	}

	@Test
	public void testWithFileUrlRelative() {

		var repository = insertRepository("file://dir/subdir");
		assertException(//
			IllegalArgumentException.class,
			"Expected an absolute path but encountered: 'dir/subdir'",
			() -> factory.create(repository));
	}

	@Test
	public void testWithSmbUrl() {

		var repository = insertRepository("smb://host/share");
		Optional<IStoredFileContentStore> store = factory.create(repository);
		assertTrue(store.isPresent());
		assertTrue(StoredFileSmbContentStore.class.isInstance(store.get()));
	}

	private AGStoredFileRepository insertRepository(String url) {

		return new AGStoredFileRepository().setUrl(url).save();
	}
}
