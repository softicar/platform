package com.softicar.platform.db.core.dbms.mysql;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import com.softicar.platform.db.core.database.IDbDatabase;
import com.softicar.platform.db.core.table.DbTableName;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class DbMysqlDatabasePurger {

	private final IDbDatabase database;

	public DbMysqlDatabasePurger(IDbDatabase database) {

		this.database = Objects.requireNonNull(database);
	}

	public void purgeAll() {

		try (DbDatabaseScope databaseScope = new DbDatabaseScope(database)) {
			DbMysqlStatements.setForeignKeyChecksEnabled(false);
			Set<String> databases = DbMysqlStatements.getDatabaseNames();
			Set<String> systemSchemas = new TreeSet<>(DbMysql.SYSTEM_SCHEMAS);
			for (String database: databases) {
				if (!systemSchemas.contains(database)) {
					purgeDatabase(database);
				}
			}
			Log.fverbose("Database purged.");
		}
	}

	private void purgeDatabase(String database) {

		Log.fverbose("Purging %s", database);
		var tableNames = new ArrayList<DbTableName>();
		try (DbResultSet resultSet = DbMysqlStatements.showTableStatusFrom(database)) {
			while (resultSet.next()) {
				tableNames.add(new DbTableName(database, resultSet.getString("Name")));
			}
		}
		DbMysqlStatements.dropTables(tableNames);
		DbMysqlStatements.dropDatabase(database);
	}
}
