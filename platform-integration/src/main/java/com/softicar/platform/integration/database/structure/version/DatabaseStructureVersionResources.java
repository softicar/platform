package com.softicar.platform.integration.database.structure.version;

import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import java.util.Collection;
import java.util.regex.Pattern;

class DatabaseStructureVersionResources {

	public static final Pattern MIGRATION_FIELD_NAME_PATTERN = Pattern.compile("^V([0-9]+)_MIGRATION.*");
	public static final Pattern MIGRATION_FILE_NAME_PATTERN = Pattern.compile("v[0-9]+-migration.sql");
	public static final Pattern STRUCTURE_FIELD_NAME_PATTERN = Pattern.compile("^V([0-9]+)_STRUCTURE.*");
	public static final Pattern STRUCTURE_FILE_NAME_PATTERN = Pattern.compile("v[0-9]+-structure.json");

	public static IResourceSupplier getLatestStructureResourceSupplier() {

		return new DatabaseStructureVersionResourcesMap().getLatestStructureResourceSupplier();
	}

	public static Collection<IResourceSupplier> getAllStructureResourceSuppliers() {

		return new DatabaseStructureVersionResourcesMap().getAllStructureResourceSuppliers();
	}
}
