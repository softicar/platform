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
	public void testGetAccessibleContentStoresWithoutRepositories() {

		var stores = StoredFileContentStores.getAccessibleContentStores();
		assertTrue(stores.isEmpty());
	}

	@Test
	public void testGetAccessibleContentStoresWithValidPrimaryRepository() {

		insertValidPrimaryRepository(getDirectoryUrl(directory1));
		var stores = StoredFileContentStores.getAccessibleContentStores();
		assertEquals(1, stores.size());
		assertTrue(StoredFileFileSystemContentStore.class.isInstance(stores.iterator().next()));
		assertEquals(directory1.getPath(), stores.iterator().next().getLocation());
	}

	@Test
	public void testGetAccessibleContentStoresWithInvalidPrimaryRepository() {

		insertInvalidPrimaryRepository();
		var stores = StoredFileContentStores.getAccessibleContentStores();
		assertTrue(stores.isEmpty());
	}

	@Test
	public void testGetAccessibleContentStoresWithMultipleRepositories() {

		insertMultipleRepositories();
		var stores = new ArrayList<>(StoredFileContentStores.getAccessibleContentStores());
		assertEquals(3, stores.size());
		assertEquals(directory2.getPath(), stores.get(0).getLocation());
		assertEquals(directory1.getPath(), stores.get(1).getLocation());
		assertEquals(directory3.getPath(), stores.get(2).getLocation());
	}

	@Test
	public void testGetPrimaryContentStore() {

		insertMultipleRepositories();
		var store = StoredFileContentStores.getPrimaryContentStore();
		assertTrue(store.isPresent());
		assertTrue(StoredFileFileSystemContentStore.class.isInstance(store.get()));
		assertEquals(directory2.getPath(), store.get().getLocation());
	}

	@Test
	public void testGetPrimaryContentStoreWithInactivePrimary() {

		insertInactivePrimaryRepository(getDirectoryUrl(directory2));
		var store = StoredFileContentStores.getPrimaryContentStore();
		assertTrue(store.isEmpty());
	}

	@Test
	public void testGetPrimaryContentStoreWithUndefinedPrimary() {

		var store = StoredFileContentStores.getPrimaryContentStore();
		assertTrue(store.isEmpty());
	}

	private void insertValidPrimaryRepository(String url) {

		makePrimary(insertRepository(url));
	}

	private void insertInactivePrimaryRepository(String url) {

		var repository = insertRepository(url);
		repository.setActive(false).save();
		makePrimary(repository);
	}

	private AGStoredFileRepository insertRepository(String url) {

		return new AGStoredFileRepository()//
			.setUrl(url)
			.save();
	}

	private void insertInvalidPrimaryRepository() {

		makePrimary(insertRepository("file:///nonexistent"));
	}

	private void insertMultipleRepositories() {

		// accessible secondary
		insertRepository(getDirectoryUrl(directory1));

		// inaccessible secondary
		insertRepository("file:///nonexistent");

		// accessible primary
		makePrimary(insertRepository(getDirectoryUrl(directory2)));

		// accessible secondary
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
