package com.softicar.platform.db.structure.mysql.column;

import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnStructure;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser.ColumnRow;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import com.softicar.platform.db.structure.utils.DbColumnCommentParser;

public class DbMysqlColumnStructure extends DbColumnStructure {

	public DbMysqlColumnStructure(IDbTableStructure tableStructure, ColumnRow columnRow) {

		super(tableStructure);

		SqlFieldType fieldType = new InternalDbMysqlColumnFieldTypeDeterminer(tableStructure, columnRow).determineFieldType();
		InternalDbMysqlColumnType columnType = new InternalDbMysqlColumnTypeDeterminer(columnRow).determineColumnType();

		setAutoIncrement(columnRow.isAutoIncrement());
		setBase(determineBase(columnRow));
		setBits(new InternalDbMysqlColumnBitsDeterminer(fieldType, columnType).determineBits());
		setCharacterSet(columnRow.getCharacterSet());
		setCollation(columnRow.getCollation());
		setComment(columnRow.getComment());
		setDefaultValue(columnRow.getDefaultValue());
		setDefaultType(columnRow.getDefaultType());
		setEnumValues(new InternalDbMysqlColumnEnumValuesDeterminer(fieldType, columnRow).determineEnumValues());
		setFieldType(fieldType);
		setLengthBits(new InternalDbMysqlColumnLengthBitsDeterminer(columnType).determineLengthBits());
		setLogicalName(dertermineLogicalName(columnRow));
		setMaxLength(new InternalDbMysqlColumnMaxLengthDeterminer(fieldType, columnRow).determineMaxLength());
		setNullable(columnRow.isNullAllowed());
		setName(columnRow.getColumnName());
		setPrecision(new InternalDbMysqlColumnPrecisionDeterminer(fieldType, columnRow).determinePrecision());
		setOnUpdateNow(columnRow.isOnUpdateNow());
		setScale(new InternalDbMysqlColumnScaleDeterminer(fieldType, columnRow).determineScale());
		setTimestamp(columnType == InternalDbMysqlColumnType.TIMESTAMP);
		setUnsigned(columnRow.isUnsigned());
	}

	private static boolean determineBase(ColumnRow columnRow) {

		return new DbColumnCommentParser(columnRow.getComment()).isBaseColumn();
	}

	private String dertermineLogicalName(ColumnRow columnRow) {

		String nameOverride = new DbColumnCommentParser(columnRow.getComment()).getNameOverride();
		return nameOverride != null? nameOverride : columnRow.getColumnName();
	}
}
