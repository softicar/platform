package com.softicar.platform.integration.database.structure.version;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.core.module.container.docker.DockerContainerController;
import com.softicar.platform.db.core.connection.DbConnection;
import com.softicar.platform.db.core.connection.DbConnectionOverrideScope;
import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.dbms.mysql.DbMySqlDatabasePurger;
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
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DatabaseStructureVersionMigrationsTest extends AbstractTest {

	private static final String MARIADB_PASSWORD = "iamroot";
	private static final String MARIADB_USER = "root";
	private static final String MARIADB_VERSION = "10.5.8";
	private static final DockerContainerController CONTAINER_CONTROLLER = new DockerContainerController(//
		"mariadb-migration-test",
		"mariadb:" + MARIADB_VERSION,
		"-e MYSQL_ROOT_PASSWORD=" + MARIADB_PASSWORD,
		"-d",
		"--rm");

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

	@Test
	public void testMigrationsCreateTargetStructures() {

		var resourcesMap = new DatabaseStructureVersionResourcesMap();
		int latestVersion = resourcesMap.getLatestVersion();

		// latest migration
		testMigrationCreatesTargetStructure(resourcesMap, latestVersion - 1, latestVersion);

		// second latest migration
		testMigrationCreatesTargetStructure(resourcesMap, latestVersion - 2, latestVersion - 1);
	}

	private void testMigrationCreatesTargetStructure(DatabaseStructureVersionResourcesMap resourcesMap, int sourceVersion, int targetVersion) {

		var sourceStructureResourceSupplier = resourcesMap.getStructureResourceSupplier(sourceVersion);
		var targetStructureResourceSupplier = resourcesMap.getStructureResourceSupplier(targetVersion);
		var migrationResourceSupplier = resourcesMap.getMigrationResourceSupplier(targetVersion);

		var sourceCreateTableStatements = getCreateTableStatements(sourceStructureResourceSupplier);
		var targetCreateTableStatements = getCreateTableStatements(targetStructureResourceSupplier);
		var migrationScript = migrationResourceSupplier.getResource().getContentTextUtf8();

		try (var connection = createConnection(); var scope = new DbConnectionOverrideScope(connection)) {
			DbMysqlStatements.setForeignKeyChecksEnabled(false);

			// create source structure
			purgeDatabase(connection);
			createTables(sourceCreateTableStatements);
			IDbDatabaseStructure sourceStructure = loadDatabaseStructure();
			assertFalse(//
				"Structure v%s creation resulted in an empty database.".formatted(sourceVersion),
				sourceStructure.getTableNames().isEmpty());

			// TODO Initialize DB content for the respective source structure version, and stop ignoring DML statements.

			// migrate source structure to target structure
			new DbStatement(new MigrationScriptPreprocessor().preprocess(migrationScript)).execute();
			IDbDatabaseStructure targetStructureViaMigration = loadDatabaseStructure();

			// create target structure
			purgeDatabase(connection);
			createTables(targetCreateTableStatements);
			IDbDatabaseStructure targetStructureViaCreation = loadDatabaseStructure();
			assertFalse(//
				"Structure v%s creation resulted in an empty database.".formatted(targetVersion),
				targetStructureViaCreation.getTableNames().isEmpty());

			var comparisonStrategy = new DbStructureEqualityComparisonStrategy(//
				IDisplayString.create("structure from creation"),
				IDisplayString.create("structure from migration"));
			var diagnosticContainer = new DbDatabaseStructureComparer(comparisonStrategy)//
				.compareAll(targetStructureViaCreation, targetStructureViaMigration);
			if (!diagnosticContainer.isEmpty()) {
				throw new AssertionError(diagnosticContainer);
			}
		}
	}

	private void purgeDatabase(DbConnection connection) {

		new DbMySqlDatabasePurger(connection.getDatabase()).purgeAll();
		var structure = loadDatabaseStructure();
		assertTrue(//
			"Database purging was incomplete. Remaining tables: %s".formatted(Imploder.implode(structure.getTableNames(), ", ")),
			structure.getTableNames().isEmpty());
	}

	private void createTables(List<CreateTableStatement> createTableStatements) {

		for (var statement: createTableStatements) {
			new DbStatement("CREATE SCHEMA IF NOT EXISTS `%s`", statement.getDatabaseName()).execute();
			new DbStatement(statement.getStatementString()).execute();
		}
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
}
