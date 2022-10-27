package com.softicar.platform.integration.database.structure.version;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

/**
 * Lists JSON-based database structure version definitions, and corresponding
 * SQL-based migration scripts.
 * <p>
 * Any listed file must either be called {@code "vX-structure.json"}, or
 * {@code "vX-migration.sql"}, with {@code "X"} being a release version number.
 * The static field names must correspond to the file names (without
 * extensions).
 * <p>
 * By definition, {@code "vX-migration.sql"} contains the necessary statements
 * to migrate the structure and content of an existing database instance from
 * version {@code "X-1"} to version {@code "X"}.
 */
@ResourceSupplierContainer
public interface PlatformDatabaseStructureVersionResource {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(PlatformDatabaseStructureVersionResource.class);

	IResourceSupplier V15_STRUCTURE = FACTORY.create("v15-structure.json");
	IResourceSupplier V16_MIGRATION = FACTORY.create("v16-migration.sql");
	IResourceSupplier V16_STRUCTURE = FACTORY.create("v16-structure.json");
	IResourceSupplier V17_MIGRATION = FACTORY.create("v17-migration.sql");
	IResourceSupplier V17_STRUCTURE = FACTORY.create("v17-structure.json");
	IResourceSupplier V18_MIGRATION = FACTORY.create("v18-migration.sql");
	IResourceSupplier V18_STRUCTURE = FACTORY.create("v18-structure.json");
	IResourceSupplier V19_MIGRATION = FACTORY.create("v19-migration.sql");
	IResourceSupplier V19_STRUCTURE = FACTORY.create("v19-structure.json");
	IResourceSupplier V20_MIGRATION = FACTORY.create("v20-migration.sql");
	IResourceSupplier V20_STRUCTURE = FACTORY.create("v20-structure.json");
	IResourceSupplier V21_MIGRATION = FACTORY.create("v21-migration.sql");
	IResourceSupplier V21_STRUCTURE = FACTORY.create("v21-structure.json");
	IResourceSupplier V22_MIGRATION = FACTORY.create("v22-migration.sql");
	IResourceSupplier V22_STRUCTURE = FACTORY.create("v22-structure.json");
	IResourceSupplier V23_MIGRATION = FACTORY.create("v23-migration.sql");
	IResourceSupplier V23_STRUCTURE = FACTORY.create("v23-structure.json");
	IResourceSupplier V24_MIGRATION = FACTORY.create("v24-migration.sql");
	IResourceSupplier V24_STRUCTURE = FACTORY.create("v24-structure.json");
	IResourceSupplier V25_MIGRATION = FACTORY.create("v25-migration.sql");
	IResourceSupplier V25_STRUCTURE = FACTORY.create("v25-structure.json");
	IResourceSupplier V26_MIGRATION = FACTORY.create("v26-migration.sql");
	IResourceSupplier V26_STRUCTURE = FACTORY.create("v26-structure.json");
}
