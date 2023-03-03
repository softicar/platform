package com.softicar.platform.core.module.file.stored.content.store;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.file.stored.repository.AGStoredFileRepository;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import org.junit.Test;

public class StoredFileContentStoresTest extends AbstractCoreTest {

	private final File directory1;
	private final File directory2;
	private final File directory3;

	public StoredFileContentStoresTest() {

		this.directory1 = createTempDirectory();
		this.directory2 = createTempDirectory();
		this.directory3 = createTempDirectory();
	}

	@Test
	public void testGetAvailableContentStoresWithoutRepositories() {

		var contentStores = StoredFileContentStores.getAvailableContentStores();
		assertTrue(contentStores.isEmpty());
	}

	@Test
	public void testGetAvailableContentStoresWithValidPrimaryRepository() {

		insertValidPrimaryRepository();
		var contentStores = StoredFileContentStores.getAvailableContentStores();
		assertEquals(1, contentStores.size());
		assertTrue(StoredFileFileSystemContentStore.class.isInstance(contentStores.iterator().next()));
	}

	@Test
	public void testGetAvailableContentStoresWithInvalidPrimaryRepository() {

		insertInvalidPrimaryRepository();
		var contentStores = StoredFileContentStores.getAvailableContentStores();
		assertTrue(contentStores.isEmpty());
	}

	@Test
	public void testGetAvailableContentStoresWithMultipleRepositories() {

		insertMultipleRepositories();
		var stores = new ArrayList<>(StoredFileContentStores.getAvailableContentStores());
		assertEquals(3, stores.size());
		assertEquals(directory2.getPath(), stores.get(0).getLocation());
		assertEquals(directory1.getPath(), stores.get(1).getLocation());
		assertEquals(directory3.getPath(), stores.get(2).getLocation());
	}

	@Test
	public void testGetPreferredAvailableContentStoreWithoutRepositories() {

		var contentStore = StoredFileContentStores.getPreferredAvailableContentStore();
		assertTrue(contentStore.isEmpty());
	}

	@Test
	public void testGetPreferredAvailableContentStoreWithValidPrimaryRepository() {

		insertValidPrimaryRepository();
		var contentStore = StoredFileContentStores.getPreferredAvailableContentStore();
		assertTrue(contentStore.isPresent());
		assertTrue(StoredFileFileSystemContentStore.class.isInstance(contentStore.get()));
	}

	@Test
	public void testGetPreferredAvailableContentStoreWithInvalidPrimaryRepository() {

		insertInvalidPrimaryRepository();
		var contentStore = StoredFileContentStores.getPreferredAvailableContentStore();
		assertTrue(contentStore.isEmpty());
	}

	@Test
	public void testGetPreferredAvailableContentStoreWithMultipleRepositories() {

		insertMultipleRepositories();
		var store = StoredFileContentStores.getPreferredAvailableContentStore();
		assertEquals(directory2.getPath(), store.get().getLocation());
	}

	private void insertValidPrimaryRepository() {

		makePrimary(insertRepository(getDirectoryUrl(directory1)));
	}

	private void insertInvalidPrimaryRepository() {

		makePrimary(insertRepository("file:///nonexistent"));
	}

	private AGStoredFileRepository insertRepository(String url) {

		return new AGStoredFileRepository()//
			.setUrl(url)
			.save();
	}

	private void insertMultipleRepositories() {

		// available secondary
		insertRepository(getDirectoryUrl(directory1));

		// unavailable secondary
		insertRepository("file:///nonexistent");

		// available primary
		makePrimary(insertRepository(getDirectoryUrl(directory2)));

		// available secondary
		insertRepository(getDirectoryUrl(directory3));
	}

	private void makePrimary(AGStoredFileRepository repository) {

		AGCoreModuleInstance//
			.getInstance()
			.setPrimaryFileRepository(repository)
			.save();
	}

	private String getDirectoryUrl(File directory) {

		return "file://" + directory.getPath();
	}

	private File createTempDirectory() {

		try {
			File directory = Files.createTempDirectory(getClass().getSimpleName()).toFile();
			directory.deleteOnExit();
			return directory;
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
