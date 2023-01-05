package com.softicar.platform.integration.database.structure.version;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.core.module.container.docker.DockerContainerController;
import com.softicar.platform.db.core.connection.DbConnection;
import com.softicar.platform.db.core.connection.DbConnectionOverrideScope;
import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.dbms.mysql.DbMysqlDatabasePurger;
import com.softicar.platform.db.core.dbms.mysql.DbMysqlStatements;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.runtime.structure.comparison.DbDatabaseStructureComparer;
import com.softicar.platform.db.runtime.structure.comparison.strategy.DbStructureEqualityComparisonStrategy;
import com.softicar.platform.db.structure.database.IDbDatabaseStructure;
import com.softicar.platform.db.structure.mysql.DbMysqlDatabaseStructureLoader;
import com.softicar.platform.integration.database.structure.DatabaseStructureTableDefinition;
import com.softicar.platform.integration.database.structure.DatabaseStructureTableDefinitionsConverter;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Base class of tests to ensure that known database migrations transform a
 * specific source database structure to a specific target database structure.
 * <p>
 * Particularly, migration <i>x</i> is ensured to transform structure version
 * <i>x</i> to structure version <i>x+1</i>.
 * <p>
 * Uses MariaDB in a local Docker container.
 * <p>
 * Any extending class should reside in an <i>integration sub project</i>; i.e.
 * a sub project that depends on all other sub projects, in a multi-project
 * build.
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractDatabaseStructureVersionMigrationsTest extends AbstractTest {

	private static final String MARIADB_PASSWORD = "iamroot";
	private static final String MARIADB_USER = "root";
	private static final String MARIADB_VERSION = "10.5.8";
	private static final DockerContainerController CONTAINER_CONTROLLER = new DockerContainerController(//
		"mariadb-migration-test",
		"mariadb:" + MARIADB_VERSION,
		"-e MYSQL_ROOT_PASSWORD=" + MARIADB_PASSWORD,
		"-d",
		"--rm");
	private final Class<?> resourceContainerClass;
	private final int numberOfRecentMigrations;

	@BeforeClass
	public static void beforeClass() {

		CONTAINER_CONTROLLER.startup();
		CONTAINER_CONTROLLER.registerRuntimeShutdownHook();

		// Wait until the containerized MariaDB server is ready.
		// TODO Find a less dumb way to avoid connecting too early.
		Sleep.sleep(Duration.ofSeconds(5));
	}

	@AfterClass
	public static void afterClass() {

		CONTAINER_CONTROLLER.shutdown();
	}

	/**
	 * @param resourceContainerClass
	 *            the {@link ResourceSupplierContainer}-annotated {@link Class}
	 *            that enumerates the structure and migration definition files
	 *            (never <i>null</i>)
	 */
	public AbstractDatabaseStructureVersionMigrationsTest(Class<?> resourceContainerClass) {

		this(resourceContainerClass, Integer.MAX_VALUE);
	}

	/**
	 * @param resourceContainerClass
	 *            the {@link ResourceSupplierContainer}-annotated {@link Class}
	 *            that enumerates the structure and migration definition files
	 *            (never <i>null</i>)
	 * @param numberOfRecentMigrations
	 *            determines how many migrations shall be tested, starting with
	 *            the most recent one (at least 1; {@link Integer#MAX_VALUE} to
	 *            test all enumerated migrations)
	 */
	public AbstractDatabaseStructureVersionMigrationsTest(Class<?> resourceContainerClass, int numberOfRecentMigrations) {

		Objects.requireNonNull(resourceContainerClass);
		if (numberOfRecentMigrations < 1) {
			throw new IllegalArgumentException();
		}

		this.numberOfRecentMigrations = numberOfRecentMigrations;
		this.resourceContainerClass = resourceContainerClass;
	}

	@Test
	public void testMigrationsCreateTargetStructures() {

		var resourcesMap = new DatabaseStructureVersionResourcesMap(resourceContainerClass);
		for (int i = 0; i < numberOfRecentMigrations; i++) {
			if (!testMigrationCreatesTargetStructure(resourcesMap, i)) {
				break;
			}
		}
	}

	/**
	 * Validates the migration to the structure version with the given reverse
	 * version index.
	 * <p>
	 * In the list of existing versions, a reverse version index of 0 refers to
	 * the latest version. A reverse version index of 1 refers to the second
	 * latest version, and so on.
	 *
	 * @param resourcesMap
	 * @param reverseVersionIndex
	 *            the reverse index of the version to migrate to
	 * @return <i>true</i> if a migration from the given source version to the
	 *         given target version was found; <i>false</i> otherwise
	 */
	private boolean testMigrationCreatesTargetStructure(DatabaseStructureVersionResourcesMap resourcesMap, int reverseVersionIndex) {

		int targetVersion = resourcesMap.getRecentVersion(reverseVersionIndex);
		int sourceVersion = resourcesMap.getRecentVersion(reverseVersionIndex + 1);

		var errorFactory = new AssertionErrorFactory(targetVersion);
		var migrationResourceSupplier = resourcesMap.getMigrationResourceSupplier(targetVersion);
		if (migrationResourceSupplier.isPresent()) {
			var sourceStructureResourceSupplier = resourcesMap.getStructureResourceSupplier(sourceVersion);
			var targetStructureResourceSupplier = resourcesMap.getStructureResourceSupplier(targetVersion);

			var sourceCreateTableStatements = getCreateTableStatements(sourceStructureResourceSupplier);
			var targetCreateTableStatements = getCreateTableStatements(targetStructureResourceSupplier);
			var migrationScript = migrationResourceSupplier.get().getResource().getContentTextUtf8();

			try (var connection = createConnection(); var scope = new DbConnectionOverrideScope(connection)) {

				// create source structure
				DbMysqlStatements.setForeignKeyChecksEnabled(false);
				purgeDatabase(connection, errorFactory);
				createTables(sourceCreateTableStatements);
				IDbDatabaseStructure sourceStructure = loadDatabaseStructure();
				if (sourceStructure.getTableNames().isEmpty()) {
					throw errorFactory.create("Structure v%s creation resulted in an empty database.".formatted(sourceVersion));
				}

				// TODO Initialize DB content for the respective source structure version, and stop ignoring DML statements.

				// migrate source structure to target structure
				DbMysqlStatements.setForeignKeyChecksEnabled(true);
				new DbStatement(new MigrationScriptPreprocessor().preprocess(migrationScript)).execute();
				IDbDatabaseStructure targetStructureViaMigration = loadDatabaseStructure();

				// create target structure
				DbMysqlStatements.setForeignKeyChecksEnabled(false);
				purgeDatabase(connection, errorFactory);
				createTables(targetCreateTableStatements);
				IDbDatabaseStructure targetStructureViaCreation = loadDatabaseStructure();
				if (targetStructureViaCreation.getTableNames().isEmpty()) {
					throw errorFactory.create("Structure v%s creation resulted in an empty database.".formatted(targetVersion));
				}

				// compare structures
				var comparisonStrategy = new DbStructureEqualityComparisonStrategy(//
					IDisplayString.format("Expected"),
					IDisplayString.format("Actual"));
				var diagnosticContainer = new DbDatabaseStructureComparer(comparisonStrategy)//
					.compareAll(targetStructureViaCreation, targetStructureViaMigration);

				if (!diagnosticContainer.isEmpty()) {
					throw errorFactory.create(diagnosticContainer);
				}
			} catch (Exception exception) {
				throw errorFactory.create(StackTraceFormatting.getStackTraceAsString(exception));
			}
			return true;
		} else {
			return false;
		}
	}

	private void purgeDatabase(DbConnection connection, AssertionErrorFactory errorFactory) {

		new DbMysqlDatabasePurger(connection.getDatabase()).purgeAll();
		var structure = loadDatabaseStructure();
		if (!structure.getTableNames().isEmpty()) {
			throw errorFactory.create("Database purging was incomplete. Remaining tables: %s".formatted(Imploder.implode(structure.getTableNames(), ", ")));
		}
	}

	private void createTables(List<CreateTableStatement> createTableStatements) {

		var multiStatement = new DbStatement();
		for (var statement: createTableStatements) {
			multiStatement.addText("CREATE SCHEMA IF NOT EXISTS `%s`;\n", statement.getDatabaseName());
			multiStatement.addText(statement.getStatementString() + "\n");
		}
		multiStatement.executeUncached();
	}

	private List<CreateTableStatement> getCreateTableStatements(IResourceSupplier structureResourceSupplier) {

		String tableDefinitionsJson = structureResourceSupplier.getResource().getContentTextUtf8();
		return new DatabaseStructureTableDefinitionsConverter()
			.convertToDefinitionList(tableDefinitionsJson)
			.stream()
			.map(DatabaseStructureTableDefinition::getCreateStatement)
			.map(CreateTableStatement::new)
			.collect(Collectors.toList());
	}

	private IDbDatabaseStructure loadDatabaseStructure() {

		return new DbMysqlDatabaseStructureLoader().load();
	}

	private DbConnection createConnection() {

		return new DbConnection(//
			DbServerType.MARIA_DB,
			CONTAINER_CONTROLLER.getContainerIpAddress() + "",
			"",
			MARIADB_USER,
			MARIADB_PASSWORD);
	}

	private static class CreateTableStatement {

		private static final Pattern DATABASE_NAME_REGEX = Pattern.compile("CREATE TABLE `(.*?)`.`.*?` .*");
		private final String statementString;
		private final String databaseName;

		public CreateTableStatement(String statementString) {

			this.statementString = statementString;
			var matcher = DATABASE_NAME_REGEX.matcher(statementString);
			if (!matcher.find()) {
				throw new IllegalArgumentException();
			}
			this.databaseName = matcher.group(1);
		}

		public String getStatementString() {

			return statementString;
		}

		public String getDatabaseName() {

			return databaseName;
		}
	}

	private static class MigrationScriptPreprocessor {

		private static final String BEGIN_DML_CONTEXT_TOKEN = "-- BEGIN DML";
		private static final String END_DML_CONTEXT_TOKEN = "-- END DML";

		/**
		 * Pre-processes a given migration script, removing all lines between
		 * {@value #BEGIN_DML_CONTEXT_TOKEN} and {@value #END_DML_CONTEXT_TOKEN}
		 * marker comments.
		 *
		 * @param migrationScript
		 *            the migration script (never <i>null</i>)
		 * @return the modified migration script (never <i>null</i>)
		 */
		public String preprocess(String migrationScript) {

			var output = new StringBuilder();
			boolean dmlContext = false;
			for (String line: migrationScript.split("\n")) {
				if (line.startsWith(BEGIN_DML_CONTEXT_TOKEN)) {
					dmlContext = true;
				}
				if (line.startsWith(END_DML_CONTEXT_TOKEN)) {
					dmlContext = false;
				}
				if (!dmlContext) {
					output.append(line);
					output.append("\n");
				}
			}
			return output.toString();
		}
	}

	private class AssertionErrorFactory {

		private final int targetVersion;

		public AssertionErrorFactory(int targetVersion) {

			this.targetVersion = targetVersion;
		}

		public AssertionError create(Object reason) {

			return new AssertionError("Failed to migrate to v%s:\n%s".formatted(targetVersion, reason));
		}
	}
}
