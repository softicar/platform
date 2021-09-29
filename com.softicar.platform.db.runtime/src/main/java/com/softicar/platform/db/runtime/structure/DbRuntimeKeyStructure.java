package com.softicar.platform.db.runtime.structure;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.structure.key.DbKeyStructure;
import com.softicar.platform.db.structure.key.DbKeyType;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.List;
import java.util.stream.Collectors;

public class DbRuntimeKeyStructure extends DbKeyStructure {

	public DbRuntimeKeyStructure(IDbTableStructure tableStructure, IDbKey<?> key) {

		super(tableStructure);

		setType(getKeyType(key));
		setName(key.getName());
		addColumnNames(getKeyColumnNames(key));
	}

	private static DbKeyType getKeyType(IDbKey<?> key) {

		if (key.isPrimaryKey()) {
			return DbKeyType.PRIMARY;
		} else if (key.isUniqueKey()) {
			return DbKeyType.UNIQUE;
		} else {
			return DbKeyType.INDEX;
		}
	}

	private static List<String> getKeyColumnNames(IDbKey<?> key) {

		return key//
			.getFields()
			.stream()
			.map(IDbField::getName)
			.collect(Collectors.toList());
	}
}
