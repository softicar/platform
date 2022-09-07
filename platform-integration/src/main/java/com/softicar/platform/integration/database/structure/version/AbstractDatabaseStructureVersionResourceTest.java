package com.softicar.platform.integration.database.structure.version;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.string.regex.Patterns;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.integration.database.structure.DatabaseStructureTableDefinitionsConverter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * Base class of tests that assert the structure of a
 * {@link ResourceSupplierContainer} that enumerates database structure version
 * definition and migration files.
 * <p>
 * Any extending class should reside in an <i>integration sub project</i>; i.e.
 * a sub project that depends on all other sub projects, in a multi-project
 * build.
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractDatabaseStructureVersionResourceTest extends AbstractTest {

	private final Class<?> resourceContainerClass;

	public AbstractDatabaseStructureVersionResourceTest(Class<?> resourceContainerClass) {

		this.resourceContainerClass = Objects.requireNonNull(resourceContainerClass);
	}

	@Test
	public void testValidResourceSupplierFieldNames() {

		getResourceSupplierFields().forEach(this::assertValidFieldName);
	}

	@Test
	public void testValidResourceSupplierFileNames() {

		getResourceSupplierFields()//
			.stream()
			.map(this::getResourceSupplier)
			.forEach(this::assertValidFileName);
	}

	@Test
	public void testParseableJsonResources() {

		var converter = new DatabaseStructureTableDefinitionsConverter();
		var resourceSuppliers = new DatabaseStructureVersionResourcesMap(resourceContainerClass).getAllStructureResourceSuppliers();
		for (var resourceSupplier: resourceSuppliers) {
			String json = resourceSupplier.getResource().getContentTextUtf8();
			String fileName = resourceSupplier.getResource().getFilename().get();
			var definitionList = converter.convertToDefinitionList(json);
			assertFalse(//
				"Failed to process '%s': No table definitions found.".formatted(fileName),
				definitionList.isEmpty());
		}
	}

	private void assertValidFieldName(Field resourceSupplierField) {

		String fieldName = resourceSupplierField.getName();
		assertTrue(//
			createInvalidFieldNameMessage(fieldName),
			Patterns
				.anyMatch(//
					fieldName,
					DatabaseStructureVersionResources.MIGRATION_FIELD_NAME_PATTERN,
					DatabaseStructureVersionResources.STRUCTURE_FIELD_NAME_PATTERN));
		assertFalse(//
			createInvalidFieldNameMessage(fieldName),
			fieldName.startsWith("V0"));
	}

	private void assertValidFileName(IResourceSupplier resourceSupplier) {

		String fileName = resourceSupplier.getResource().getFilename().get();
		assertTrue(//
			createInvalidFileNameMessage(fileName),
			Patterns
				.anyMatch(//
					fileName,
					DatabaseStructureVersionResources.MIGRATION_FILE_NAME_PATTERN,
					DatabaseStructureVersionResources.STRUCTURE_FILE_NAME_PATTERN));
		assertFalse(//
			createInvalidFileNameMessage(fileName),
			fileName.startsWith("v0"));
	}

	private String createInvalidFieldNameMessage(String fieldName) {

		return "Invalid field name: '%s'".formatted(fieldName);
	}

	private String createInvalidFileNameMessage(String fileName) {

		return "Invalid file name: '%s'".formatted(fileName);
	}

	private List<Field> getResourceSupplierFields() {

		return Arrays//
			.asList(resourceContainerClass.getDeclaredFields())
			.stream()
			.filter(this::isResourceSupplierField)
			.collect(Collectors.toList());
	}

	private boolean isResourceSupplierField(Field field) {

		return IResourceSupplier.class.isAssignableFrom(field.getType());
	}

	private IResourceSupplier getResourceSupplier(Field resourceSupplierField) {

		try {
			Object value = resourceSupplierField.get(null);
			return IResourceSupplier.class.cast(value);
		} catch (IllegalArgumentException | IllegalAccessException exception) {
			throw new RuntimeException(exception);
		}
	}
}
