package com.softicar.platform.integration.database.structure.version;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.utils.ReflectionUtils;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;

public class DatabaseStructureVersionResourcesMap {

	private final Map<Integer, IResourceSupplier> structureMap;
	private final Map<Integer, IResourceSupplier> migrationMap;

	public DatabaseStructureVersionResourcesMap() {

		this.structureMap = new TreeMap<>();
		this.migrationMap = new TreeMap<>();

		for (Field field: DatabaseStructureVersionResource.class.getDeclaredFields()) {
			new ResourceSupplierFieldProcessor(field)//
				.processField(createStructureFieldMatcher(field), structureMap::put)
				.processField(createMigrationFieldMatcher(field), migrationMap::put);
		}
	}

	private static class ResourceSupplierFieldProcessor {

		private final Field field;

		public ResourceSupplierFieldProcessor(Field field) {

			this.field = field;
		}

		public ResourceSupplierFieldProcessor processField(Matcher matcher, BiConsumer<Integer, IResourceSupplier> downstream) {

			Object value = ReflectionUtils.getStaticValue(field);
			if (IResourceSupplier.class.isInstance(value) && matcher.matches()) {
				var resourceSupplier = IResourceSupplier.class.cast(value);
				int version = Integer.parseInt(matcher.group(1));
				downstream.accept(version, resourceSupplier);
			}
			return this;
		}
	}

	public IResourceSupplier getMigrationResourceSupplier(int version) {

		return Optional
			.ofNullable(migrationMap.get(version))
			.orElseThrow(() -> new SofticarException("Failed to find migration to database version %s.", version));
	}

	public IResourceSupplier getStructureResourceSupplier(int version) {

		return Optional
			.ofNullable(structureMap.get(version))
			.orElseThrow(() -> new SofticarException("Failed to find structure of database version %s.", version));
	}

	public IResourceSupplier getLatestStructureResourceSupplier() {

		return getStructureResourceSupplier(getLatestVersion());
	}

	public Collection<IResourceSupplier> getAllStructureResourceSuppliers() {

		return structureMap.values();
	}

	public int getLatestVersion() {

		return Collections.max(structureMap.keySet());
	}

	private Matcher createStructureFieldMatcher(Field field) {

		return DatabaseStructureVersionResources.STRUCTURE_FIELD_NAME_PATTERN.matcher(field.getName());
	}

	private Matcher createMigrationFieldMatcher(Field field) {

		return DatabaseStructureVersionResources.MIGRATION_FIELD_NAME_PATTERN.matcher(field.getName());
	}
}
