package com.softicar.platform.db.runtime.structure.comparison.strategy;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.runtime.structure.comparison.DbTestTableStructure;
import com.softicar.platform.db.runtime.structure.comparison.diagnostic.DbStructureComparisonDiagnosticContainer;
import com.softicar.platform.db.runtime.structure.comparison.table.DbTableStructureComparer;
import com.softicar.platform.db.runtime.structure.comparison.table.DbTableStructureComparerConfiguration;
import com.softicar.platform.db.runtime.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnStructure;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.enums.DbEnumTableRowStructure;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowValue;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyStructure;
import com.softicar.platform.db.structure.key.DbKeyStructure;
import com.softicar.platform.db.structure.key.DbKeyType;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Objects;

public class AbstractDbStructureComparisonStrategyTest extends AbstractDbTest {

	private static final String COLUMN_NAME = "someColumn";
	private static final String COLUMN_NAME_ENUM_TABLE_VALUE = "valueColumn";
	private static final String COLUMN_NAME_IGNORED = "ignoredColumn";
	private static final String COLUMN_NAME_OTHER = "someOtherColumn";
	private static final String CONSTRAINT_NAME = "someConstraint";
	private static final String CONSTRAINT_NAME_OTHER = "someOtherConstraint";
	private static final String KEY_NAME = "someKey";
	private static final String KEY_NAME_OTHER = "someOtherKey";
	private static final String FOREIGN_DATABASE_NAME = "someForeignDatabase";
	private static final String FOREIGN_DATABASE_NAME_OTHER = "someOtherForeignDatabase";
	private static final String FOREIGN_TABLE_NAME = "someForeignTable";
	private static final String FOREIGN_TABLE_NAME_OTHER = "someOtherForeignTable";

	private final IDbStructureComparisonStrategy strategy;
	protected final DbTestTableStructure reference;
	protected final DbTestTableStructure sample;
	protected final DbTestTableStructure referenceEnum;
	protected final DbTestTableStructure sampleEnum;
	private DbStructureComparisonDiagnosticContainer container;

	public AbstractDbStructureComparisonStrategyTest(IDbStructureComparisonStrategy strategy) {

		this.strategy = strategy;
		this.reference = createTableStructure();
		this.sample = createTableStructure();
		this.referenceEnum = createEnumTableStructure();
		this.sampleEnum = createEnumTableStructure();
		this.container = null;
	}

	protected void executeComparison() {

		this.container = createTableComparer().compareAll();
	}

	protected void executeColumnComparison() {

		this.container = createTableComparer().compareColumnsOnly();
	}

	protected void executeConstraintComparison() {

		this.container = createTableComparer().compareForeignKeysOnly();
	}

	protected void executeKeyComparison() {

		this.container = createTableComparer().compareKeysOnly();
	}

	protected void executeEnumTableRowComparison() {

		this.container = createEnumTableComparer().compareEnumTableRowsOnly();
	}

	protected DbColumnStructure addColumnStructure(DbTestTableStructure tableStructure) {

		return tableStructure.addColumn(COLUMN_NAME, SqlFieldType.INTEGER);
	}

	protected DbColumnStructure addOtherColumnStructure(DbTestTableStructure tableStructure) {

		return tableStructure.addColumn(COLUMN_NAME_OTHER, SqlFieldType.LONG);
	}

	protected DbColumnStructure addColumnStructure(DbTestTableStructure tableStructure, String columnName, SqlFieldType fieldType) {

		return tableStructure.addColumn(columnName, fieldType);
	}

	protected DbColumnStructure addIgnoredColumnStructure(DbTestTableStructure tableStructure) {

		return addColumnStructure(tableStructure, COLUMN_NAME_IGNORED, SqlFieldType.STRING).setComment("@ignore@").setNullable(true);
	}

	protected DbForeignKeyStructure addConstraintStructure(DbTestTableStructure tableStructure) {

		return tableStructure.addForeignKey(CONSTRAINT_NAME, FOREIGN_DATABASE_NAME, FOREIGN_TABLE_NAME);
	}

	protected DbForeignKeyStructure addOtherConstraintStructure(DbTestTableStructure tableStructure) {

		return tableStructure.addForeignKey(CONSTRAINT_NAME_OTHER, FOREIGN_DATABASE_NAME_OTHER, FOREIGN_TABLE_NAME_OTHER);
	}

	protected DbKeyStructure addKeyStructure(DbTestTableStructure tableStructure, DbKeyType keyType) {

		return tableStructure.addKey(KEY_NAME, keyType);
	}

	protected DbKeyStructure addOtherKeyStructure(DbTestTableStructure tableStructure, DbKeyType keyType) {

		return tableStructure.addKey(KEY_NAME_OTHER, keyType);
	}

	protected DbEnumTableRowStructure addEnumTableRowStructure(DbTestTableStructure tableStructure, int id, String name, Integer value) {

		return addEnumTableRowStructure(tableStructure, id, name, value, null);
	}

	protected DbEnumTableRowStructure addEnumTableRowStructure(DbTestTableStructure tableStructure, int id, String name, Integer value, String ignoredValue) {

		IDbColumnStructure valueColumn = getColumnStructureByName(tableStructure, COLUMN_NAME_ENUM_TABLE_VALUE);
		DbEnumTableRowStructure structure = tableStructure.addEnumTableRow();
		structure.setValue(tableStructure.getColumnByPhysicalName("id").get(), IDbEnumTableRowValue.valueOf(id));
		structure.setValue(tableStructure.getColumnByPhysicalName("name").get(), IDbEnumTableRowValue.valueOf(name));
		structure.setValue(valueColumn, IDbEnumTableRowValue.valueOf(value));
		if (ignoredValue != null) {
			IDbColumnStructure ignoredColumn = getColumnStructureByName(tableStructure, COLUMN_NAME_IGNORED);
			structure.setValue(ignoredColumn, IDbEnumTableRowValue.valueOf(ignoredValue));
		}
		return structure;
	}

	protected DbForeignKeyStructure createForeignKeyStructure(IDbTableStructure tableStructure) {

		return new DbForeignKeyStructure(tableStructure)//
			.setName(CONSTRAINT_NAME)
			.setForeignTableName(new DbTableName(FOREIGN_DATABASE_NAME, FOREIGN_TABLE_NAME));
	}

	protected DbForeignKeyStructure createOtherForeignKeyStructure(IDbTableStructure tableStructure) {

		return new DbForeignKeyStructure(tableStructure)//
			.setName(CONSTRAINT_NAME_OTHER)
			.setForeignTableName(new DbTableName(FOREIGN_DATABASE_NAME_OTHER, FOREIGN_TABLE_NAME_OTHER));
	}

	private DbTableStructureComparer createTableComparer() {

		IDbTableStructureComparerConfiguration configuration = new DbTableStructureComparerConfiguration(strategy, reference, sample);
		return new DbTableStructureComparer(configuration);
	}

	private DbTableStructureComparer createEnumTableComparer() {

		IDbTableStructureComparerConfiguration configuration = new DbTableStructureComparerConfiguration(strategy, referenceEnum, sampleEnum);
		return new DbTableStructureComparer(configuration);
	}

	private String getContainerContent() {

		return "Diagnostics in the container:\n" + container.toString();
	}

	private DbTestTableStructure createTableStructure() {

		return new DbTestTableStructure("db", "table");
	}

	private DbTestTableStructure createEnumTableStructure() {

		DbTestTableStructure structure = new DbTestTableStructure("db", "enumTable");
		structure.setEnumTable(true);
		structure.setComment("@enum@");
		structure.addColumn("id", SqlFieldType.INTEGER).setLogicalName("id").setAutoIncrement(true);
		structure.addColumn("name", SqlFieldType.STRING).setLogicalName("name");
		structure.addColumn(COLUMN_NAME_ENUM_TABLE_VALUE, SqlFieldType.INTEGER).setNullable(true);
		structure.addKey(null, DbKeyType.PRIMARY).addColumnName("id");
		return structure;
	}

	private IDbColumnStructure getColumnStructureByName(DbTestTableStructure tableStructure, String columnName) {

		return tableStructure//
			.getColumns()
			.stream()
			.filter(it -> it.getNameOrThrow().equals(columnName))
			.findAny()
			.get();
	}

	public class Asserter {

		public Asserter() {

			Objects.requireNonNull(container);
		}

		public Asserter assertErrors(int expectedCount) {

			assertEquals(getContainerContent(), expectedCount, container.getErrorCount());
			return this;
		}

		public Asserter assertWarnings(int expectedCount) {

			assertEquals(getContainerContent(), expectedCount, container.getWarningCount());
			return this;
		}

		public Asserter assertInfos(int expectedCount) {

			assertEquals(getContainerContent(), expectedCount, container.getInfoCount());
			return this;
		}

		public Asserter assertOnlyErrors(int expectedCount) {

			assertErrors(expectedCount);
			assertNoWarnings();
			assertNoInfos();
			return this;
		}

		public Asserter assertOnlyWarnings(int expectedCount) {

			assertWarnings(expectedCount);
			assertNoErrors();
			assertNoInfos();
			return this;
		}

		public Asserter assertOnlyInfos(int expectedCount) {

			assertInfos(expectedCount);
			assertNoErrors();
			assertNoWarnings();
			return this;
		}

		public Asserter assertNoDiagnostics() {

			assertNoErrors();
			assertNoWarnings();
			assertNoInfos();
			return this;
		}

		public Asserter assertNoErrors() {

			assertTrue(getContainerContent(), container.isEmptyErrors());
			return this;
		}

		public Asserter assertNoWarnings() {

			assertTrue(getContainerContent(), container.isEmptyWarnings());
			return this;
		}

		public Asserter assertNoInfos() {

			assertTrue(getContainerContent(), container.isEmptyInfos());
			return this;
		}
	}
}
