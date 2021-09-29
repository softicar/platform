package com.softicar.platform.db.structure.mysql.column;

import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser.ColumnRow;
import java.util.Collections;
import java.util.List;

class InternalDbMysqlColumnEnumValuesDeterminer {

	private final SqlFieldType fieldType;
	private final List<String> typeParameters;

	public InternalDbMysqlColumnEnumValuesDeterminer(SqlFieldType fieldType, ColumnRow columnRow) {

		this.fieldType = fieldType;
		this.typeParameters = columnRow.getTypeParameters();
	}

	public List<String> determineEnumValues() {

		if (fieldType == SqlFieldType.ENUM) {
			return Collections.unmodifiableList(typeParameters);
		} else {
			return Collections.emptyList();
		}
	}
}
