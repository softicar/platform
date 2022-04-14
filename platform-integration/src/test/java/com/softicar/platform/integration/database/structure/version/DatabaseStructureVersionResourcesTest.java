package com.softicar.platform.integration.database.structure.version;

import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.testing.AbstractTest;
import java.lang.reflect.Field;
import org.junit.Test;

public class DatabaseStructureVersionResourcesTest extends AbstractTest {

	@Test
	public void testGetLatestStructureResourceSupplier() {

		IResourceSupplier resourceSupplier = DatabaseStructureVersionResources.getLatestStructureResourceSupplier();
		assertNotNull(resourceSupplier);

		try {
			Field[] fields = DatabaseStructureVersionResource.class.getDeclaredFields();
			IResourceSupplier lastResourceSupplier = IResourceSupplier.class.cast(fields[fields.length - 1].get(null));
			assertSame(lastResourceSupplier, resourceSupplier);
		} catch (IllegalArgumentException | IllegalAccessException exception) {
			throw new RuntimeException(exception);
		}
	}
}
