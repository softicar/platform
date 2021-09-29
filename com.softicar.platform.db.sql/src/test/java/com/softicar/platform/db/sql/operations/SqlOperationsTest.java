package com.softicar.platform.db.sql.operations;

import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.database.DbDatabaseBuilder;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import com.softicar.platform.db.core.database.IDbDatabaseScope;
import com.softicar.platform.db.sql.test.AbstractSqlBuildableTest;
import com.softicar.platform.db.sql.test.SqlTestEntity;
import org.junit.Test;

public class SqlOperationsTest extends AbstractSqlBuildableTest {

	public SqlOperationsTest() {

		setAlias("t");
	}

	@Test
	public void testCastToIntegerWithMySql() {

		try (IDbDatabaseScope scope = new DbDatabaseScope(
			new DbDatabaseBuilder()//
				.setServerType(DbServerType.MYSQL)
				.build())) {

			build(SqlOperations.CAST_SIGNED.create(SqlTestEntity.STRING));
			assertText("CAST(t.`String` AS SIGNED)");
		}
	}

	@Test
	public void testCastToIntegerWithH2Memory() {

		try (IDbDatabaseScope scope = new DbDatabaseScope(
			new DbDatabaseBuilder()//
				.setServerType(DbServerType.H2_MEMORY)
				.build())) {

			build(SqlOperations.CAST_SIGNED.create(SqlTestEntity.STRING));
			assertText("CAST(t.`String` AS SIGNED)");
		}
	}

	@Test
	public void testCastToIntegerWithSqlServer2000() {

		try (IDbDatabaseScope scope = new DbDatabaseScope(
			new DbDatabaseBuilder()//
				.setServerType(DbServerType.MSSQL_SERVER_2000)
				.build())) {

			build(SqlOperations.CAST_SIGNED.create(SqlTestEntity.STRING));
			assertText("CAST(t.`String` AS INT)");
		}
	}
}
