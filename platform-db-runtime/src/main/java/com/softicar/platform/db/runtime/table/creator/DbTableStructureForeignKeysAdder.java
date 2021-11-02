package com.softicar.platform.db.runtime.table.creator;

import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.database.DbCurrentDatabase;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.runtime.structure.DbRuntimeTableStructure;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.structure.utils.DbAlterTableAddForeignKeysSqlGenerator;

public class DbTableStructureForeignKeysAdder {

	private final IDbTable<?, ?> table;

	public DbTableStructureForeignKeysAdder(IDbTable<?, ?> table) {

		this.table = table;
	}

	public void addForeignKeys() {

		new DbStatement()//
			.addTable(table)
			.addText(
				new DbAlterTableAddForeignKeysSqlGenerator(new DbRuntimeTableStructure(table))//
					.setIsH2Database(DbCurrentDatabase.get().getServerType() == DbServerType.H2_MEMORY)
					.getAlterTableStatement())
			.execute();
	}
}
