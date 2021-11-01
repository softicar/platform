package com.softicar.platform.db.sql.statement;

import com.softicar.platform.db.sql.test.SqlTestEntity;
import org.junit.Test;

public class SqlMultiInsertTest {

	@Test
	public void testSingleMultiInsert() {

		SqlTestInsert insert = new SqlTestInsert(SqlTestEntity.TABLE);
		insert.set(SqlTestEntity.ID, 23);
		insert.set(SqlTestEntity.INTEGER, 44);
		insert.set(SqlTestEntity.FOREIGN, null);

		insert.assertSqlText("INSERT INTO `db`.`test` (`id`, `Integer`, `Foreign`) VALUES (?, ?, ?)");
		insert.assertSqlParameters(23, 44, null);
	}

	@Test
	public void testMultiInsert() {

		SqlTestInsert insert = new SqlTestInsert(SqlTestEntity.TABLE);
		insert.set(SqlTestEntity.ID, 23);
		insert.set(SqlTestEntity.INTEGER, 44);
		insert.set(SqlTestEntity.FOREIGN, null);
		insert.nextRow();
		insert.set(SqlTestEntity.ID, 24);
		insert.set(SqlTestEntity.INTEGER, 55);
		insert.set(SqlTestEntity.FOREIGN, null);

		insert.assertSqlText("INSERT INTO `db`.`test` (`id`, `Integer`, `Foreign`) VALUES (?, ?, ?), (?, ?, ?)");
		insert.assertSqlParameters(23, 44, null, 24, 55, null);
	}
}
