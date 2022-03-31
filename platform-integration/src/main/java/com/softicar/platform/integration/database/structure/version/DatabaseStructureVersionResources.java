package com.softicar.platform.integration.database.structure.version;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DatabaseStructureVersionResources {

	public static final Pattern MIGRATION_FIELD_NAME_PATTERN = Pattern.compile("^V([0-9]+)_MIGRATION.*");
	public static final Pattern MIGRATION_FILE_NAME_PATTERN = Pattern.compile("v[0-9]+-migration.sql");
	public static final Pattern STRUCTURE_FIELD_NAME_PATTERN = Pattern.compile("^V([0-9]+)_STRUCTURE.*");
	public static final Pattern STRUCTURE_FILE_NAME_PATTERN = Pattern.compile("v[0-9]+-structure.json");

	public static IResourceSupplier getLatestStructureResourceSupplier() {

		try {
			int latestVersion = 0;
			IResourceSupplier latestStructureResourceSupplier = null;
			for (Field field: DatabaseStructureVersionResource.class.getDeclaredFields()) {
				Object reference = field.get(null);
				Matcher matcher = STRUCTURE_FIELD_NAME_PATTERN.matcher(field.getName());
				if (IResourceSupplier.class.isInstance(reference) && matcher.matches()) {
					int version = Integer.parseInt(matcher.group(1));
					if (version > latestVersion) {
						latestVersion = version;
						latestStructureResourceSupplier = IResourceSupplier.class.cast(reference);
					}
				}
			}
			return Objects.requireNonNull(latestStructureResourceSupplier);
		} catch (IllegalAccessException exception) {
			throw new SofticarDeveloperException(exception);
		}
	}

	public static Collection<IResourceSupplier> getAllStructureResourceSuppliers() {

		try {
			var resourceSuppliers = new ArrayList<IResourceSupplier>();
			for (Field field: DatabaseStructureVersionResource.class.getDeclaredFields()) {
				Object reference = field.get(null);
				Matcher matcher = STRUCTURE_FIELD_NAME_PATTERN.matcher(field.getName());
				if (IResourceSupplier.class.isInstance(reference) && matcher.matches()) {
					resourceSuppliers.add(IResourceSupplier.class.cast(reference));
				}
			}
			return resourceSuppliers;
		} catch (IllegalAccessException exception) {
			throw new SofticarDeveloperException(exception);
		}
	}
}
