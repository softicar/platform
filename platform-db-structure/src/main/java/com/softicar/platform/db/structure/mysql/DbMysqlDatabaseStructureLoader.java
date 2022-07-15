package com.softicar.platform.db.structure.mysql;

import com.softicar.platform.db.core.dbms.mysql.DbMysql;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.core.table.DbTableNameFetcher;
import com.softicar.platform.db.structure.database.DbDatabaseStructure;
import com.softicar.platform.db.structure.database.IDbDatabaseStructure;
import com.softicar.platform.db.structure.view.DbViewStructuresLoader;
import java.util.Collection;

public class DbMysqlDatabaseStructureLoader {

	public IDbDatabaseStructure load() {

		DbDatabaseStructure databaseStructure = new DbDatabaseStructure();

		new DbMysqlTableStructuresLoader(getTableNames()).loadAll().values().forEach(databaseStructure::addTableStructure);
		new DbViewStructuresLoader().loadAll().values().forEach(databaseStructure::addViewStructure);

		return databaseStructure;
	}

	private Collection<DbTableName> getTableNames() {

		return new DbTableNameFetcher()//
			.addIgnoredTableSchemas(DbMysql.SYSTEM_SCHEMAS)
			.addTableType("TABLE")
			.addTableType("BASE TABLE")
			.fetchAllTableNames();
	}
}
