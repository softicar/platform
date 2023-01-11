package com.softicar.platform.db.runtime.table.creator;

import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.database.DbCurrentDatabase;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.runtime.structure.DbRuntimeTableStructure;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.structure.utils.DbTableStructureSqlGenerator;

public class DbTableStructureCreator {

	private final IDbTable<?, ?> table;
	private boolean setIfNotExists;
	private boolean skipDatabaseCreation;
	private boolean skipForeignKeyCreation;
	private Integer autoIncrement;
	private final DbRuntimeTableStructure tableStructure;

	public DbTableStructureCreator(IDbTable<?, ?> table) {

		this.table = table;
		this.tableStructure = new DbRuntimeTableStructure(table);
		this.setIfNotExists = false;
		this.skipForeignKeyCreation = false;
		this.skipDatabaseCreation = false;
		this.autoIncrement = null;
	}

	public DbTableStructureCreator setIfNotExists(boolean setIfNotExists) {

		this.setIfNotExists = setIfNotExists;
		return this;
	}

	public DbTableStructureCreator setSkipDatabaseCreation(boolean skipDatabaseCreation) {

		this.skipDatabaseCreation = skipDatabaseCreation;
		return this;
	}

	public DbTableStructureCreator setSkipForeignKeyCreation(boolean skipForeignKeyCreation) {

		this.skipForeignKeyCreation = skipForeignKeyCreation;
		return this;
	}

	public DbTableStructureCreator setAutoIncrement(Integer autoIncrement) {

		this.autoIncrement = autoIncrement;
		return this;
	}

	public void create() {

		if (!skipDatabaseCreation) {
			createDatabaseIfNotExists();
		}
		createTable();
	}

	private void createTable() {

		new DbStatement()//
			.addTable(table)
			.addText(
				new DbTableStructureSqlGenerator(tableStructure)//
					.setAutoIncrement(autoIncrement)
					.setIfNotExists(setIfNotExists)
					.setIsH2Database(DbCurrentDatabase.get().getServerType() == DbServerType.H2_MEMORY)
					.setSkipForeignKeyCreation(skipForeignKeyCreation)
					.getCreateTableStatement())
			.execute();
	}

	private void createDatabaseIfNotExists() {

		new DbStatement()//
			.addText(new DbTableStructureSqlGenerator(tableStructure).getCreateDatabaseStatement())
			.execute();
	}
}
