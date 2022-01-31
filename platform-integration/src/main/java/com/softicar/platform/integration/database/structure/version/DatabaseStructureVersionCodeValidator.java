package com.softicar.platform.integration.database.structure.version;

import com.softicar.platform.common.core.java.classpath.JavaClasspathRootFolder;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validation.output.JavaCodeViolations;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.integration.database.structure.DatabaseStructureJsonFromClasspathExtractor;
import com.softicar.platform.integration.database.structure.DatabaseStructureTableConflictingDefinition;
import com.softicar.platform.integration.database.structure.DatabaseStructureTableDefinition;
import com.softicar.platform.integration.database.structure.DatabaseStructureTableDefinitionsComparer;
import com.softicar.platform.integration.database.structure.DatabaseStructureTableDefinitionsConverter;
import java.io.File;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Ensures that the database structure in the classpath matches the most recent
 * structure definition listed in {@link DatabaseStructureVersionResource}.
 * <p>
 * Does nothing unless {@link #INTEGRATION_PROJECT_NAME_JSON_PATH} and
 * {@link #TABLE_PACKAGE_PREFIX_JSON_PATH} are defined in the
 * {@link JavaCodeValidationEnvironment}.
 *
 * @author Alexander Schmidt
 */
@JavaCodeValidator
public class DatabaseStructureVersionCodeValidator implements IJavaCodeValidator {

	private static final String INTEGRATION_PROJECT_NAME_JSON_PATH = "$.integrationProjectName";
	private static final String TABLE_PACKAGE_PREFIX_JSON_PATH = "$.tablePackagePrefix";

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		var violations = new JavaCodeViolations();
		var reader = environment.getConfigurationJsonValueReader();
		Optional<String> integrationProjectName = reader.read(INTEGRATION_PROJECT_NAME_JSON_PATH);
		Optional<String> tablePackagePrefix = reader.read(TABLE_PACKAGE_PREFIX_JSON_PATH);

		if (integrationProjectName.isPresent() && tablePackagePrefix.isPresent()) {
			if (getClasspathDirectories(environment).contains("/" + integrationProjectName.get() + "/")) {
				var resourceSupplier = DatabaseStructureVersionResources.getLatestStructureResourceSupplier();
				String jsonFromResource = loadStructureJsonFromResource(resourceSupplier);
				String jsonFromClasspath = loadStructureJsonFromClasspath(tablePackagePrefix.get());

				var converter = new DatabaseStructureTableDefinitionsConverter();
				var definitionsFromResource = converter.convertToDefinitionList(jsonFromResource);
				var definitionsFromClasspath = converter.convertToDefinitionList(jsonFromClasspath);

				var comparer = new DatabaseStructureTableDefinitionsComparer().compare(definitionsFromResource, definitionsFromClasspath);

				if (!comparer.isNoDifference()) {
					String latestResourceFilename = resourceSupplier.getResource().getFilename().get();

					var firstOnlyDefinitions = comparer.getFirstOnlyDefinitions();
					var secondOnlyDefinitions = comparer.getSecondOnlyDefinitions();
					var conflictingDefinitions = comparer.getConflictingDefinitions();

					var message = new StringBuilder()//
						.append("The database structure in '%s' does not match the database structure in the classpath.\n\n".formatted(latestResourceFilename));

					if (!firstOnlyDefinitions.isEmpty()) {
						message//
							.append("!! Tables that exist only in '%s' (%s):\n\n".formatted(latestResourceFilename, firstOnlyDefinitions.size()))
							.append(firstOnlyDefinitions.stream().map(this::createProblemString).collect(Collectors.joining()));
					}

					if (!secondOnlyDefinitions.isEmpty()) {
						message//
							.append("!! Tables that exist only in the classpath (%s):\n\n".formatted(secondOnlyDefinitions.size()))
							.append(secondOnlyDefinitions.stream().map(this::createProblemString).collect(Collectors.joining()));
					}

					if (!conflictingDefinitions.isEmpty()) {
						message//
							.append("!! Tables with conflicting definitions (%s):\n\n".formatted(conflictingDefinitions.size()))
							.append(conflictingDefinitions.stream().map(it -> createProblemString(it, latestResourceFilename)).collect(Collectors.joining()));
					}

					message//
						.append(
							"!! If the structure described in '%s' was already released, save the current structure to a new file, with incremented version number.\n"
								.formatted(latestResourceFilename));

					message//
						.append(
							"!! Use '%s' to derive the current structure from the classpath.\n"
								.formatted(DatabaseStructureJsonFromClasspathExtractor.class.getSimpleName()));

					violations.addViolation(message.toString());
				}
			}

			violations.throwExceptionIfNotEmpty();
		}
	}

	private String createProblemString(DatabaseStructureTableDefinition definition) {

		return "******** %s ********\n%s\n\n"
			.formatted(//
				definition.getTableName(),
				definition.getCreateStatement());
	}

	private String createProblemString(DatabaseStructureTableConflictingDefinition conflictingDefinition, String resourceFilename) {

		return "******** %s ********\n> [%s] >\n%s\n> [Classpath] >\n%s\n\n"
			.formatted(//
				conflictingDefinition.getTableName(),
				resourceFilename,
				conflictingDefinition.getFirstCreateStatement(),
				conflictingDefinition.getSecondCreateStatement());
	}

	private String loadStructureJsonFromClasspath(String tablePackagePrefix) {

		return new DatabaseStructureJsonFromClasspathExtractor(tablePackagePrefix).extractJson();
	}

	private String loadStructureJsonFromResource(IResourceSupplier resourceSupplier) {

		return resourceSupplier.getTextUtf8();
	}

	private String getClasspathDirectories(JavaCodeValidationEnvironment environment) {

		return environment//
			.getClassPath()
			.getRootFolders()
			.stream()
			.map(JavaClasspathRootFolder::getFile)
			.map(File::getPath)
			.collect(Collectors.joining(","));
	}
}
