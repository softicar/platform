package com.softicar.platform.integration.database.structure.version;

import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.testing.AbstractTest;
import java.lang.reflect.Field;
import org.junit.Test;

public class DatabaseStructureVersionResourcesMapTest extends AbstractTest {

	private final DatabaseStructureVersionResourcesMap map;

	public DatabaseStructureVersionResourcesMapTest() {

		this.map = new DatabaseStructureVersionResourcesMap(PlatformDatabaseStructureVersionResource.class);
	}

	@Test
	public void testGetMigrationResourceSupplier() {

		int latestVersion = map.getRecentVersion(0);
		var resourceSupplier = map.getMigrationResourceSupplier(latestVersion).get();

		assertEquals(//
			"v%s-migration.sql".formatted(latestVersion),
			resourceSupplier.getResource().getFilename().get());
	}

	@Test
	public void testGetStructureResourceSupplier() {

		int targetVersion = map.getRecentVersion(0);
		int sourceVersion = map.getRecentVersion(1);
		var sourceResourceSupplier = map.getStructureResourceSupplier(sourceVersion);
		var targetResourceSupplier = map.getStructureResourceSupplier(targetVersion);

		assertEquals(//
			"v%s-structure.json".formatted(sourceVersion),
			sourceResourceSupplier.getResource().getFilename().get());
		assertEquals(//
			"v%s-structure.json".formatted(targetVersion),
			targetResourceSupplier.getResource().getFilename().get());
	}

	@Test
	public void testGetLatestStructureResourceSupplier() {

		var resourceSupplier = map.getLatestStructureResourceSupplier();
		assertNotNull(resourceSupplier);

		try {
			Field[] fields = PlatformDatabaseStructureVersionResource.class.getDeclaredFields();
			IResourceSupplier lastResourceSupplier = IResourceSupplier.class.cast(fields[fields.length - 1].get(null));
			assertSame(lastResourceSupplier, resourceSupplier);
		} catch (IllegalArgumentException | IllegalAccessException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Test
	public void testGetAllStructureResourceSuppliers() {

		var resourceSuppliers = map.getAllStructureResourceSuppliers();

		assertTrue(resourceSuppliers.size() >= 3);
		resourceSuppliers.forEach(it -> {
			it.getResource().getFilename().get().matches("v[0-9]+-structure.json");
		});
	}

	@Test
	public void testGetRecentVersion() {

		int latestVersion = map.getRecentVersion(0);
		int previousVersion = map.getRecentVersion(1);

		// Expecting explicit version numbers here would entail touching the test every time a new version is added.
		// Hence this heuristic approach.
		assertTrue(latestVersion > 1);
		assertTrue(previousVersion > 1);
		assertTrue(latestVersion > previousVersion);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetRecentVersionWithReverseIndexOutOfBounds() {

		map.getRecentVersion(Integer.MAX_VALUE);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetRecentVersionWithReverseIndexNegative() {

		map.getRecentVersion(-1);
	}
}
