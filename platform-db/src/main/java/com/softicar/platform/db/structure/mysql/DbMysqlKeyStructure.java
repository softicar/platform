package com.softicar.platform.db.structure.mysql;

import com.softicar.platform.db.structure.key.DbKeyStructure;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser.KeyRow;
import com.softicar.platform.db.structure.table.IDbTableStructure;

public class DbMysqlKeyStructure extends DbKeyStructure {

	public DbMysqlKeyStructure(IDbTableStructure tableStructure, KeyRow keyRow) {

		super(tableStructure);

		setType(keyRow.getKeyType());
		setName(keyRow.getKeyName());
		addColumnNames(keyRow.getKeyColumns());
	}
}
