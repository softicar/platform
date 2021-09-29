package com.softicar.platform.db.runtime.table.creator;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.profiler.DbConnectionTestProfiler;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.table.configuration.DbTableConfiguration;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class DbAutomaticTableCreatorTest extends AbstractDbTest {

	private final DbAutomaticTableCreator tableCreator;

	public DbAutomaticTableCreatorTest() {

		this.tableCreator = new DbAutomaticTableCreator(() -> 31);
	}

	@Test
	public void test() {

		DbConnectionTestProfiler profiler = new DbConnectionTestProfiler();
		DbConnections.setProfiler(profiler);

		tableCreator.beforeExecution(new DbStatement().addTable(ObjectA.TABLE));

		profiler
			.assertStatements(//
				"CREATE SCHEMA IF NOT EXISTS `db`",
				"CREATE SCHEMA IF NOT EXISTS `db`",
				"CREATE TABLE `db`.`b` (`id` INT NOT NULL AUTO_INCREMENT(52),PRIMARY KEY (`id`));",
				"CREATE TABLE `db`.`a` (`id` INT NOT NULL AUTO_INCREMENT(31),`b` INT NOT NULL,PRIMARY KEY (`id`),FOREIGN KEY (`b`) REFERENCES `db`.`b` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT);",
				"INSERT INTO `db`.`b` (`id`) VALUES (?)",
				"SELECT t.`id` FROM `db`.`b` t WHERE ((t.`id`) IN ((?)))",
				"INSERT INTO `db`.`a` (`id`, `b`) VALUES (?, ?)");
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
					.set(ObjectA.ID, 7)
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
					.set(ObjectB.ID, 13)
					.executeWithoutIdGeneration();
			});
		}
	}
}
