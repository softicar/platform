package com.softicar.platform.db.structure.utils;

import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.structure.foreign.key.IDbForeignKeyStructure;

public class DbForeignKeyStructureSqlGenerator {

	private final IDbForeignKeyStructure foreignKeyStructure;
	private boolean isAlterTableMode;

	public DbForeignKeyStructureSqlGenerator(IDbForeignKeyStructure foreignKeyStructure) {

		this.foreignKeyStructure = foreignKeyStructure;
		this.isAlterTableMode = false;
	}

	public DbForeignKeyStructureSqlGenerator setIsAlterTableMode(boolean isAlterTableMode) {

		this.isAlterTableMode = isAlterTableMode;
		return this;
	}

	@Override
	public String toString() {

		return new StringBuilder()//
			.append(isAlterTableMode? "ADD " : "")
			.append(getConstraintClause())
			.append("FOREIGN KEY (")
			.append(Imploder.implodeQuoted(foreignKeyStructure.getColumns(), ",", "`"))
			.append(") REFERENCES `")
			.append(foreignKeyStructure.getForeignTableName().getDatabaseName())
			.append("`.`")
			.append(foreignKeyStructure.getForeignTableName().getSimpleName())
			.append("` (")
			.append(Imploder.implodeQuoted(foreignKeyStructure.getForeignColumns(), ",", "`"))
			.append(") ON DELETE ")
			.append(foreignKeyStructure.getOnDeleteAction().toSql())
			.append(" ON UPDATE ")
			.append(foreignKeyStructure.getOnUpdateAction().toSql())
			.toString();
	}

	private String getConstraintClause() {

		return foreignKeyStructure//
			.getName()
			.map(name -> String.format("CONSTRAINT `%s` ", name))
			.orElse("");
	}
}
