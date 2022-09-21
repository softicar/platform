package com.softicar.platform.db.runtime.structure.comparison;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.runtime.structure.comparison.diagnostic.DbStructureComparisonDiagnosticContainer;
import com.softicar.platform.db.runtime.structure.comparison.strategy.DbStructureCompatibilityComparisonStrategy;
import com.softicar.platform.db.structure.database.DbDatabaseStructure;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser;
import com.softicar.platform.db.structure.table.DbTableStructure;
import com.softicar.platform.db.structure.view.DbViewStructure;
import org.junit.Test;

public class DbDatabaseStructureComparerTest extends AbstractTest {

	private final DbDatabaseStructure dbmsStructure;
	private final DbDatabaseStructure codeStructure;
	private final DbDatabaseStructureComparer comparer;

	public DbDatabaseStructureComparerTest() {

		this.comparer = new DbDatabaseStructureComparer(new DbStructureCompatibilityComparisonStrategy());
		this.dbmsStructure = new DbDatabaseStructure();
		this.codeStructure = new DbDatabaseStructure();
	}

	@Test
	public void testWithAliasView() {

		dbmsStructure.addTableStructure(parseTableStructure("CREATE TABLE `db`.`foo` (`id` INT AUTO_INCREMENT)"));
		dbmsStructure.addViewStructure(new DbViewStructure(new DbTableName("db", "bar"), "SELECT foo.id AS id FROM db.foo"));

		codeStructure.addTableStructure(parseTableStructure("CREATE TABLE `db`.`bar` (`id` INT AUTO_INCREMENT)"));

		DbStructureComparisonDiagnosticContainer diagnosticContainer = comparer.compareAll(dbmsStructure, codeStructure);

		assertEquals(0, diagnosticContainer.getErrorCount());
		assertEquals(1, diagnosticContainer.getWarningCount());
		assertEquals(0, diagnosticContainer.getInfoCount());
	}

	private DbTableStructure parseTableStructure(String createTableStatement) {

		return new DbMysqlCreateTableStatementParser(createTableStatement).parse();
	}
}
