package com.softicar.platform.db.core.metadata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Represents the JDBC metadata of a database table column.
 *
 * @author Oliver Richers
 */
public class DbMetadataColumn {

	private final DbMetadataTable table;
	private final String columnName;
	private final int columnIndex;
	private final int dataType;
	private final String typeName;
	private final boolean nullable;
	private final boolean autoIncrement;
	private final String defaultValue;
	private final int decimalDigits;
	private final int radix;
	private final int charOctetLength;
	private final int columnSize;
	private final String columnRemarks;

	protected DbMetadataColumn(DbMetadataTable table, ResultSet resultSet) throws SQLException {

		this.table = table;
		this.columnName = resultSet.getString("COLUMN_NAME");
		this.columnIndex = resultSet.getInt("ORDINAL_POSITION");
		this.dataType = resultSet.getInt("DATA_TYPE");
		this.typeName = resultSet.getString("TYPE_NAME");
		this.nullable = resultSet.getString("IS_NULLABLE").equals("YES");
		this.autoIncrement = resultSet.getString("IS_AUTOINCREMENT").equals("YES");
		this.defaultValue = resultSet.getString("COLUMN_DEF");
		this.decimalDigits = resultSet.getInt("DECIMAL_DIGITS");
		this.radix = resultSet.getInt("NUM_PREC_RADIX");
		this.charOctetLength = resultSet.getInt("CHAR_OCTET_LENGTH");
		this.columnSize = resultSet.getInt("COLUMN_SIZE");
		this.columnRemarks = resultSet.getString("REMARKS");
	}

	/**
	 * Returns the database table that this column belongs to.
	 *
	 * @return the database table (never null)
	 */
	public DbMetadataTable getTable() {

		return table;
	}

	/**
	 * Returns the name of this column.
	 *
	 * @return the column name (never null)
	 */
	public String getColumnName() {

		return columnName;
	}

	/**
	 * Returns the index of this column in the database table.
	 *
	 * @return the column index starting at one
	 */
	public int getColumnIndex() {

		return columnIndex;
	}

	/**
	 * Returns the JDBC data type of this column.
	 * <p>
	 * The types are defined in {@link Types}.
	 *
	 * @return the column data type
	 */
	public int getDataType() {

		return dataType;
	}

	/**
	 * Returns the data source dependent type name of this column.
	 *
	 * @return the column type name (never null)
	 */
	public String getTypeName() {

		return typeName;
	}

	/**
	 * Returns whether this column allows NULL values.
	 *
	 * @return whether this column allows NULL values
	 */
	public boolean isNullable() {

		return nullable;
	}

	/**
	 * Returns whether this column is an AUTO_INCREMENT column.
	 *
	 * @return whether this is and AUTO_INCREMENT column
	 */
	public boolean isAutoIncrement() {

		return autoIncrement;
	}

	/**
	 * Returns the default value of this column.
	 *
	 * @return the column default value as {@link String} (may be null)
	 */
	public String getDefaultValue() {

		return defaultValue;
	}

	/**
	 * Returns the number of decimal digits for columns with decimal places.
	 *
	 * @return the decimal digits count
	 */
	public int getDecimalDigits() {

		return decimalDigits;
	}

	/**
	 * Returns the radix for numeric columns.
	 *
	 * @return the radix (usually 2 or 10)
	 */
	public int getRadix() {

		return radix;
	}

	/**
	 * Returns the character limit of this column.
	 *
	 * @return the maximum number of bytes for this column
	 */
	public int getCharOctetLength() {

		return charOctetLength;
	}

	/**
	 * Returns the size of this column.
	 *
	 * @return the column size
	 */
	public int getColumnSize() {

		return columnSize;
	}

	/**
	 * Returns an optional comment on this column.
	 *
	 * @return an optional column comment (may be null)
	 */
	public String getColumnRemarks() {

		return columnRemarks;
	}
}
