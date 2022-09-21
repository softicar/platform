package com.softicar.platform.db.runtime.table.creator;

import com.softicar.platform.db.core.SofticarSqlException;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.table.configuration.DbTableConfiguration;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;

/**
 * Tests for {@link DbAutomaticTableCreator}.
 * <p>
 * Testing of {@link DbAutomaticTableCreator} is only possible together with
 * {@link AbstractDbTest} when the creation of referenced tables is involved, so
 * we use the {@link DbAutomaticTableCreator} instance from
 * {@link AbstractDbTest} here.
 *
 * @author Oliver Richers
 */
public class DbAutomaticTableCreatorTest extends AbstractDbTest {

	private static final int DEFAULT_A_ID = 7;
	private static final int DEFAULT_B_ID = 13;

	public DbAutomaticTableCreatorTest() {

		setAutoIncrementSupplier(() -> 31);
	}

	@Test
	public void testTableCreationThroughSelect() {

		assertEquals("[]", getTables().toString());

		var count = ObjectB.TABLE.createSelect().count();

		assertEquals(1, count);
		assertEquals("[`db`.`b`]", getTables().toString());
	}

	@Test
	public void testTableCreationWithReferencedTable() {

		assertEquals("[]", getTables().toString());

		var count = ObjectA.TABLE.createSelect().count();

		assertEquals(1, count);
		assertEquals("[`db`.`a`, `db`.`b`]", getTables().toString());
	}

	@Test
	public void testTableCreationWithTransactionCommit() {

		assertEquals("[]", getTables().toString());

		try (var transaction = new DbTransaction()) {
			// create table and object in B
			var b = new ObjectB().save();
			assertEquals("[`db`.`b`]", getTables().toString());

			// create table and object in A
			new ObjectA().setB(b).save();
			assertEquals("[`db`.`a`, `db`.`b`]", getTables().toString());

			transaction.commit();
		}

		assertEquals("[`db`.`a`, `db`.`b`]", getTables().toString());
		assertEquals("[[id: 7, b: 13], [id: 31, b: 31]]", ObjectA.TABLE.loadAll().toString());
		assertEquals("[[id: 13], [id: 31]]", ObjectB.TABLE.loadAll().toString());
	}

	@Test
	public void testTableCreationWithTransactionRollback() {

		assertEquals("[]", getTables().toString());

		try (var transaction = new DbTransaction()) {
			// create table and object in B
			var b = new ObjectB().save();
			assertEquals("[`db`.`b`]", getTables().toString());

			// create table and object in A
			new ObjectA().setB(b).save();
			assertEquals("[`db`.`a`, `db`.`b`]", getTables().toString());

			transaction.rollback();
		}

		assertEquals("[`db`.`a`, `db`.`b`]", getTables().toString());
		assertEquals(DEFAULT_A_ID, assertOne(ObjectA.TABLE.loadAll()).getId());
		assertEquals(DEFAULT_B_ID, assertOne(ObjectB.TABLE.loadAll()).getId());
	}

	private Set<DbTableName> getTables() {

		var tables = new TreeSet<DbTableName>();
		try (var resultSet = DbConnections.getMetaData().getTables(null, "db", null, null)) {
			while (resultSet.next()) {
				var schema = resultSet.getString("TABLE_SCHEM");
				var name = resultSet.getString("TABLE_NAME");
				tables.add(new DbTableName(schema, name));
			}
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
		return tables;
	}

	// ------------------------------ ObjectA ------------------------------ //

	private static class ObjectA extends AbstractDbObject<ObjectA> {

		public static final DbObjectTableBuilder<ObjectA, ObjectA> BUILDER = new DbObjectTableBuilder<>("db", "a", ObjectA::new, ObjectA.class);
		public static final IDbIdField<ObjectA> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
		public static final IDbForeignField<ObjectA, ObjectB> B = BUILDER.addForeignField("b", o -> o.b, (o, v) -> o.b = v, ObjectB.ID);
		public static final ObjectATable TABLE = new ObjectATable(BUILDER);
		private Integer id;
		private ObjectB b;

		@Override
		public IDbObjectTable<ObjectA> table() {

			return TABLE;
		}

		public ObjectA setB(ObjectB b) {

			return setValue(ObjectA.B, b);
		}
	}

	private static class ObjectATable extends DbObjectTable<ObjectA> {

		public ObjectATable(IDbObjectTableBuilder<ObjectA> builder) {

			super(builder);
		}

		@Override
		protected void customizeTableConfiguration(DbTableConfiguration<ObjectA, Integer> configuration) {

			configuration.setDataInitializer(() -> {
				ObjectA.TABLE//
					.createInsert()
					.set(ObjectA.ID, DEFAULT_A_ID)
					.set(ObjectA.B, ObjectB.TABLE.get(13))
					.executeWithoutIdGeneration();
			});
		}
	}

	// ------------------------------ ObjectB ------------------------------ //

	private static class ObjectB extends AbstractDbObject<ObjectB> {

		public static final DbObjectTableBuilder<ObjectB, ObjectB> BUILDER = new DbObjectTableBuilder<>("db", "b", ObjectB::new, ObjectB.class);
		public static final IDbIdField<ObjectB> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
		public static final ObjectBTable TABLE = new ObjectBTable(BUILDER);
		private Integer id;

		@Override
		public IDbObjectTable<ObjectB> table() {

			return TABLE;
		}
	}

	private static class ObjectBTable extends DbObjectTable<ObjectB> {

		public ObjectBTable(IDbObjectTableBuilder<ObjectB> builder) {

			super(builder);
		}

		@Override
		protected void customizeTableConfiguration(DbTableConfiguration<ObjectB, Integer> configuration) {

			configuration.setDataInitializer(() -> {
				ObjectB.TABLE//
					.createInsert()
					.set(ObjectB.ID, DEFAULT_B_ID)
					.executeWithoutIdGeneration();
			});
		}
	}
}
