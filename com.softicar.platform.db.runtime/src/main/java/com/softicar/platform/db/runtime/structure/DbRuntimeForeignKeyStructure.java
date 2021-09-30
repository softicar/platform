package com.softicar.platform.db.runtime.structure;

import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;

public class DbRuntimeForeignKeyStructure extends DbForeignKeyStructure {

	public DbRuntimeForeignKeyStructure(IDbTableStructure tableStructure, IDbForeignRowField<?, ?, ?> foreignRowField) {

		super(tableStructure);

		setName(foreignRowField.getForeignKeyName());
		setForeignTableName(foreignRowField.getTargetTable().getFullName());
		addColumnPair(foreignRowField.getName(), foreignRowField.getTargetField().getName());
		setOnDeleteAction(foreignRowField.getOnDelete());
		setOnUpdateAction(foreignRowField.getOnUpdate());
	}

}
