package com.softicar.platform.db.structure.column;

import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.foreign.key.IDbForeignKeyStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import com.softicar.platform.db.structure.utils.DbColumnCommentParser;
import com.softicar.platform.db.structure.utils.DbColumnStructureSqlGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DbColumnStructure implements IDbColumnStructure {

	private final IDbTableStructure tableStructure;
	private boolean base;
	private int bits;
	private String collation;
	private String characterSet;
	private String comment;
	private String defaultValue;
	private DbColumnDefaultType defaultType;
	private List<String> enumValues;
	private SqlFieldType fieldType;
	private int lengthBits;
	private String logicalName;
	private int maxLength;
	private String name;
	private int precision;
	private int scale;
	private boolean autoIncrement;
	private boolean nullable;
	private boolean onUpdateNow;
	private boolean timestamp;
	private boolean unsigned;

	public DbColumnStructure(IDbTableStructure tableStructure) {

		this.tableStructure = Objects.requireNonNull(tableStructure);
		this.defaultType = DbColumnDefaultType.NONE;
		this.enumValues = Collections.emptyList();
	}

	public DbColumnStructure(IDbTableStructure tableStructure, IDbColumnStructure columnStructure) {

		this.tableStructure = Objects.requireNonNull(tableStructure);
		this.autoIncrement = columnStructure.isAutoIncrement();
		this.base = columnStructure.isBaseColumn();
		this.bits = columnStructure.getBits();
		this.characterSet = columnStructure.getCharacterSet();
		this.collation = columnStructure.getCollation();
		this.comment = columnStructure.getComment();
		this.defaultType = columnStructure.getDefaultType();
		this.defaultValue = columnStructure.getDefaultValue();
		this.enumValues = new ArrayList<>(columnStructure.getEnumValues());
		this.fieldType = columnStructure.getFieldType();
		this.lengthBits = columnStructure.getLengthBits();
		this.logicalName = columnStructure.getLogicalName();
		this.maxLength = columnStructure.getMaxLength();
		this.name = columnStructure.getNameOrThrow();
		this.nullable = columnStructure.isNullable();
		this.onUpdateNow = columnStructure.isOnUpdateNow();
		this.precision = columnStructure.getPrecision();
		this.scale = columnStructure.getScale();
		this.timestamp = columnStructure.isTimestamp();
		this.unsigned = columnStructure.isUnsigned();
	}

	public DbColumnStructure(IDbColumnStructure columnStructure) {

		this(columnStructure.getTableStructure(), columnStructure);
	}

	@Override
	public void validate() {

		Objects.requireNonNull(fieldType, "No column field type specified.");
		Objects.requireNonNull(logicalName, "No logical column name specified.");
		Objects.requireNonNull(name, "No column name specified.");
	}

	@Override
	public int getBits() {

		return bits;
	}

	@Override
	public String getCollation() {

		return collation;
	}

	@Override
	public String getCharacterSet() {

		return characterSet;
	}

	@Override
	public String getComment() {

		return comment;
	}

	@Override
	public String getDefaultValue() {

		return defaultValue;
	}

	@Override
	public DbColumnDefaultType getDefaultType() {

		return defaultType;
	}

	@Override
	public List<String> getEnumValues() {

		return enumValues;
	}

	@Override
	public SqlFieldType getFieldType() {

		return fieldType;
	}

	@Override
	public Optional<IDbForeignKeyStructure> getForeignKeyStructure() {

		for (IDbForeignKeyStructure foreignKeyStructure: tableStructure.getForeignKeys()) {
			List<String> columnList = foreignKeyStructure.getColumns();
			if (columnList.size() == 1 && columnList.get(0).equalsIgnoreCase(name)) {
				return Optional.of(foreignKeyStructure);
			}
		}
		return Optional.empty();
	}

	@Override
	public int getLengthBits() {

		return lengthBits;
	}

	@Override
	public String getLogicalName() {

		return logicalName;
	}

	@Override
	public int getMaxLength() {

		return maxLength;
	}

	@Override
	public Optional<String> getName() {

		return Optional.of(name);
	}

	@Override
	public int getPrecision() {

		return precision;
	}

	@Override
	public int getScale() {

		return scale;
	}

	@Override
	public IDbTableStructure getTableStructure() {

		return tableStructure;
	}

	@Override
	public boolean isAutoIncrement() {

		return autoIncrement;
	}

	@Override
	public boolean isBaseColumn() {

		return base;
	}

	@Override
	public boolean isForeignKey() {

		return getForeignKeyStructure().isPresent();
	}

	@Override
	public boolean isIgnored() {

		return new DbColumnCommentParser(comment).isIgnoreOverride();
	}

	@Override
	public boolean isNullable() {

		return nullable;
	}

	@Override
	public boolean isOnUpdateNow() {

		return onUpdateNow;
	}

	@Override
	public boolean isTimestamp() {

		return timestamp;
	}

	@Override
	public boolean isUnsigned() {

		return unsigned;
	}

	@Override
	public String toString() {

		return new DbColumnStructureSqlGenerator(this).toString();
	}

	// ------------------------------ mutators ------------------------------ //

	public DbColumnStructure setAutoIncrement(boolean autoIncrement) {

		this.autoIncrement = autoIncrement;
		return this;
	}

	public DbColumnStructure setBase(boolean base) {

		this.base = base;
		return this;
	}

	public DbColumnStructure setBits(int bits) {

		this.bits = bits;
		return this;
	}

	public DbColumnStructure setCollation(String collation) {

		this.collation = collation;
		return this;
	}

	public DbColumnStructure setCharacterSet(String characterSet) {

		this.characterSet = characterSet;
		return this;
	}

	public DbColumnStructure setComment(String comment) {

		this.comment = comment;
		return this;
	}

	public DbColumnStructure setDefaultValue(String defaultValue) {

		this.defaultValue = defaultValue;
		return this;
	}

	public DbColumnStructure setDefaultType(DbColumnDefaultType defaultType) {

		this.defaultType = defaultType;
		return this;
	}

	public DbColumnStructure setEnumValues(Collection<String> enumValues) {

		this.enumValues = new ArrayList<>(enumValues);
		return this;
	}

	public DbColumnStructure setFieldType(SqlFieldType fieldType) {

		this.fieldType = fieldType;
		return this;
	}

	public DbColumnStructure setLengthBits(int lengthBits) {

		this.lengthBits = lengthBits;
		return this;
	}

	public DbColumnStructure setLogicalName(String logicalName) {

		this.logicalName = logicalName;
		return this;
	}

	public DbColumnStructure setMaxLength(int maxLength) {

		this.maxLength = maxLength;
		return this;
	}

	public DbColumnStructure setName(String name) {

		this.name = name;
		return this;
	}

	public DbColumnStructure setNullable(boolean nullable) {

		this.nullable = nullable;
		return this;
	}

	public DbColumnStructure setPrecision(int precision) {

		this.precision = precision;
		return this;
	}

	public DbColumnStructure setOnUpdateNow(boolean onUpdateNow) {

		this.onUpdateNow = onUpdateNow;
		return this;
	}

	public DbColumnStructure setScale(int scale) {

		this.scale = scale;
		return this;
	}

	public DbColumnStructure setTimestamp(boolean timestamp) {

		this.timestamp = timestamp;
		return this;
	}

	public DbColumnStructure setUnsigned(boolean unsigned) {

		this.unsigned = unsigned;
		return this;
	}
}
