package com.softicar.platform.db.structure.utils;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.key.DbKeyType;
import com.softicar.platform.db.structure.key.IDbKeyStructure;
import java.util.List;
import java.util.stream.Collectors;

public class DbKeyStructureSqlGenerator {

	private final IDbKeyStructure keyStructure;
	private final DbKeyType keyType;
	private boolean isH2Database;

	public DbKeyStructureSqlGenerator(IDbKeyStructure keyStructure) {

		this.keyStructure = keyStructure;
		this.keyType = keyStructure.getType();
		this.isH2Database = false;
	}

	public DbKeyStructureSqlGenerator setH2Database(boolean h2Database) {

		this.isH2Database = h2Database;
		return this;
	}

	@Override
	public String toString() {

		List<String> columnNames = keyStructure//
			.getColumns()
			.stream()
			.map(IDbColumnStructure::getNameOrThrow)
			.collect(Collectors.toList());
		return new StringBuilder()//
			.append(getKeyTypeDeclaration())
			.append(getKeyNameDeclaration())
			.append(" (")
			.append(Imploder.implodeQuoted(columnNames, ", ", "`"))
			.append(")")
			.toString();
	}

	private String getKeyNameDeclaration() {

		return keyStructure//
			.getName()
			.map(name -> String.format(" `%s`", getQualifyKeyNameIfNecessary(name)))
			.orElse("");
	}

	private String getQualifyKeyNameIfNecessary(String name) {

		String tableNamePrefix = keyStructure.getTableStructure().getTableName().getCanonicalName().replace('.', '$');
		return isH2Database && !name.startsWith(tableNamePrefix)? tableNamePrefix + '$' + name : name;
	}

	private String getKeyTypeDeclaration() {

		switch (keyType) {
		case PRIMARY:
			return "PRIMARY KEY";
		case UNIQUE:
			return "UNIQUE KEY";
		case INDEX:
			return "KEY";
		}
		throw new SofticarUnknownEnumConstantException(keyStructure.getType());
	}
}
