package com.softicar.platform.db.structure.column;

import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.foreign.key.IDbForeignKeyStructure;
import com.softicar.platform.db.structure.table.IDbTableStructureFeature;
import java.util.List;
import java.util.Optional;

/**
 * Describes the structure of a database table column.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public interface IDbColumnStructure extends IDbTableStructureFeature {

	/**
	 * Returns the logical name of this column.
	 * <p>
	 * The logical name is the name that is used in code generation. It may be
	 * equal to the physical name.
	 *
	 * @return the logical column name (never null)
	 */
	String getLogicalName();

	/**
	 * Returns the {@link SqlFieldType} of this column.
	 *
	 * @return the type (never null)
	 */
	SqlFieldType getFieldType();

	/**
	 * Returns a {@link List} of all valid enum values of this column.
	 * <p>
	 * This method is only intended for columns of type
	 * {@link SqlFieldType#ENUM}. For any other type, an empty {@link List} is
	 * returned.
	 *
	 * @return all valid enum values as {@link String} (never null)
	 */
	List<String> getEnumValues();

	/**
	 * Returns the bit width of this column, that is 8, 16, 24, 32 or 64.
	 * <p>
	 * This method is only intended for columns of type
	 * {@link SqlFieldType#INTEGER} and {@link SqlFieldType#LONG}. For any other
	 * type, 0 is returned.
	 *
	 * @return the bit width of this column; 0 if this is not an integer column,
	 *         or if no bit width was specified
	 */
	int getBits();

	/**
	 * Returns whether this column is unsigned or signed.
	 * <p>
	 * This method is only intended for columns of type
	 * {@link SqlFieldType#INTEGER} and {@link SqlFieldType#LONG}. For any other
	 * type, <i>false</i> is returned.
	 *
	 * @return <i>true</i> if this column is unsigned; <i>false</i> otherwise
	 */
	boolean isUnsigned();

	/**
	 * Returns the maximum allowed length of this column.
	 * <p>
	 * This method is only intended for columns of type
	 * {@link SqlFieldType#BYTE_ARRAY} and {@link SqlFieldType#STRING}. For any
	 * other type, 0 is returned.
	 *
	 * @return the maximum length of this column; 0 if this is not a byte-array
	 *         or string column, or if no maximum length was specified
	 */
	int getMaxLength();

	/**
	 * Returns the length bits of this column.
	 * <p>
	 * This method is only intended for columns of type
	 * {@link SqlFieldType#BYTE_ARRAY} and {@link SqlFieldType#STRING}. For any
	 * other type, 0 is returned.
	 *
	 * @return the number of length bits of this column; 0 if this is not a
	 *         byte-array or string column, or if no length bits were specified
	 */
	int getLengthBits();

	/**
	 * Returns the character set of this column.
	 * <p>
	 * This method is only intended for columns of type
	 * {@link SqlFieldType#STRING} or {@link SqlFieldType#ENUM}. For any other
	 * type, null is returned.
	 *
	 * @return the character set (may be null)
	 */
	String getCharacterSet();

	/**
	 * Returns the collation of this column.
	 * <p>
	 * This method is only intended for columns of type
	 * {@link SqlFieldType#STRING}. For any other type, null is returned.
	 *
	 * @return the collation (may be null)
	 */
	String getCollation();

	/**
	 * Returns the number of significant decimal digits of this column.
	 * <p>
	 * This method is only intended for columns of type
	 * {@link SqlFieldType#BIG_DECIMAL}. For any other type, 0 is returned.
	 *
	 * @return the precision of this column; 0 if this is not a decimal column,
	 *         or if no precision was specified
	 */
	int getPrecision();

	/**
	 * Returns the number of fractional decimal digits of this column.
	 * <p>
	 * This method is only intended for columns of type
	 * {@link SqlFieldType#BIG_DECIMAL}. For any other type, 0 is returned.
	 *
	 * @return the scale of this column; 0 if this is not a decimal column, or
	 *         if no scale was specified
	 */
	int getScale();

	/**
	 * Returns whether this column may contain null values.
	 *
	 * @return <i>true</i> if this column may contain null values; <i>false</i>
	 *         otherwise
	 */
	boolean isNullable();

	/**
	 * Returns whether this column is an auto-incrementing column.
	 *
	 * @return <i>true</i> if this column is auto-incrementing; <i>false</i>
	 *         otherwise
	 */
	boolean isAutoIncrement();

	/**
	 * Returns whether this column is a base column.
	 *
	 * @return <i>true</i> if this column is a base column; <i>false</i>
	 *         otherwise
	 */
	boolean isBaseColumn();

	/**
	 * Returns the first single-column foreign key on this column.
	 * <p>
	 * The returned foreign key does not necessarily point to the primary key of
	 * the target table.
	 *
	 * @return the single-column foreign key
	 */
	Optional<IDbForeignKeyStructure> getForeignKeyStructure();

	/**
	 * Returns whether this column constitutes a foreign key reference.
	 * <p>
	 * Returns <i>true</i> if {@link #getForeignKeyStructure()} returns
	 * something, and <i>false</i> otherwise.
	 *
	 * @return <i>true</i> if this column is a foreign key reference;
	 *         <i>false</i> otherwise
	 */
	boolean isForeignKey();

	/**
	 * Returns the {@link DbColumnDefaultType} of this column.
	 *
	 * @return the {@link DbColumnDefaultType} of this column (never null)
	 */
	DbColumnDefaultType getDefaultType();

	/**
	 * Returns the literal default value of this column.
	 * <p>
	 * Only relevant if {@link #getDefaultType()} returns
	 * {@link DbColumnDefaultType#NORMAL}. For any other type, null is returned.
	 *
	 * @return the literal default value of this column (may be null)
	 */
	String getDefaultValue();

	/**
	 * Returns whether this column was marked as to be ignored.
	 *
	 * @return <i>true</i> if this column shall be ignored; <i>false</i>
	 *         otherwise
	 */
	boolean isIgnored();

	/**
	 * Returns whether this column is assigned the current timestamp whenever
	 * the table row is updated.
	 * <p>
	 * This method is only intended for columns of type
	 * {@link SqlFieldType#DAY_TIME}. For any other type, <i>false</i> is
	 * returned.
	 *
	 * @return <i>true</i> if this field is automatically updated to the current
	 *         point in time; <i>false</i> otherwise
	 */
	boolean isOnUpdateNow();

	/**
	 * Returns whether this column is based on the <b>DATETIME</b> or
	 * <b>TIMESTAMP</b> type.
	 * <p>
	 * This method is only intended for columns of type
	 * {@link SqlFieldType#DAY_TIME}. For any other type, <i>false</i> is
	 * returned.
	 *
	 * @return <i>true</i> if this is a <b>TIMESTAMP</b> column; <i>false</i>
	 *         otherwise
	 */
	boolean isTimestamp();

	/**
	 * Returns the comment which is specified for this column.
	 *
	 * @return the comment of this column (may be null)
	 */
	String getComment();
}
