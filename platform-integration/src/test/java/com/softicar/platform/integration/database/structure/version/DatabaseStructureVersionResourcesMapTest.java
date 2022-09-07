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

		int latestVersion = map.getLatestVersion();
		var resourceSupplier = map.getMigrationResourceSupplier(latestVersion).get();

		assertEquals(//
			"v%s-migration.sql".formatted(latestVersion),
			resourceSupplier.getResource().getFilename().get());
	}

	@Test
	public void testGetStructureResourceSupplier() {

		int latestVersion = map.getLatestVersion();
		var sourceResourceSupplier = map.getStructureResourceSupplier(latestVersion - 1);
		var targetResourceSupplier = map.getStructureResourceSupplier(latestVersion);

		assertEquals(//
			"v%s-structure.json".formatted(latestVersion - 1),
			sourceResourceSupplier.getResource().getFilename().get());
		assertEquals(//
			"v%s-structure.json".formatted(latestVersion),
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
	public void testGetLatestVersion() {

		int latestVersion = map.getLatestVersion();

		// Cannot really test this without adapting the test every time a new version is created.
		assertTrue(latestVersion > 1);
	}
}
