package com.softicar.platform.db.core;

import com.softicar.platform.common.container.matrix.AsciiTable;
import com.softicar.platform.common.container.matrix.AsciiTable.Alignment;
import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.TreeSet;

/**
 * An improved version of {@link ResultSet}.
 *
 * @author Oliver Richers
 */
public class DbResultSet implements AutoCloseable {

	private static final int MAX_ASCII_TABLE_ROWS = 100000;
	private final ResultSet resultSet;

	public DbResultSet(ResultSet resultSet) {

		this.resultSet = resultSet;
	}

	public static void checkRowLimit(int rows) {

		if (rows > MAX_ASCII_TABLE_ROWS) {
			throw new SofticarException("Limit of %s rows reached. Are you sure you want this many rows?", MAX_ASCII_TABLE_ROWS);
		}
	}

	public AsciiTable toAsciiTable() {

		try {
			// fetch all rows
			ResultSetMetaData metaData = getMetaData();
			int cols = metaData.getColumnCount();
			ArrayList<Object[]> rows = new ArrayList<>();
			while (next()) {
				Object[] row = new Object[cols];
				for (int i = 0; i < cols; ++i) {
					row[i] = get(i + 1);
				}
				rows.add(row);
				checkRowLimit(rows.size());
			}

			// create table
			AsciiTable table = new AsciiTable(cols);
			for (int i = 0; i < cols; ++i) {
				table.add(metaData.getColumnName(i + 1), Alignment.ALIGN_CENTER);
			}
			for (Object[] row: rows) {
				for (int i = 0; i < cols; ++i) {
					table.add(row[i]);
				}
			}
			return table;
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	// -------------------------------- GET -------------------------------- //

	public Object get(int columnIndex) {

		try {
			return resultSet.getObject(columnIndex);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public Object get(String columnName) {

		try {
			return resultSet.getObject(columnName);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	// -------------------------------- GET BOOLEAN -------------------------------- //

	public Boolean getBoolean(int columnIndex) {

		return DbCasting.getBoolean(get(columnIndex));
	}

	public Boolean getBoolean(String columnName) {

		return DbCasting.getBoolean(get(columnName));
	}

	// -------------------------------- GET NUMBER -------------------------------- //

	public Number getNumber(int columnIndex) {

		return DbCasting.getNumber(get(columnIndex));
	}

	public Number getNumber(String columnName) {

		return DbCasting.getNumber(get(columnName));
	}

	// -------------------------------- GET INT -------------------------------- //

	/**
	 * Returns the integer value of the field with the specified index.
	 *
	 * @return the value or 0 if the database field is null
	 */
	public int getInt(int columnIndex) {

		try {
			return resultSet.getInt(columnIndex);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	/**
	 * Returns the integer value of the field with the specified name.
	 *
	 * @return the value or 0 if the database field is null
	 */
	public int getInt(String columnName) {

		try {
			return resultSet.getInt(columnName);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	// -------------------------------- GET INTEGER -------------------------------- //

	/**
	 * Returns the integer value of the field with the specified index.
	 * <p>
	 * If the type of the value is not Integer, this will cast the value to
	 * Number and call {@link Number}.intValue.
	 *
	 * @return the value or null if the database field is null
	 */
	public Integer getInteger(int columnIndex) {

		return DbCasting.getInteger(get(columnIndex));
	}

	/**
	 * Returns the integer value of the field with the specified name.
	 * <p>
	 * If the type of the value is not Integer, this will cast the value to
	 * Number and call {@link Number}.intValue.
	 *
	 * @return the value or null if the database field is null
	 */
	public Integer getInteger(String columnName) {

		return DbCasting.getInteger(get(columnName));
	}

	// -------------------------------- GET LONG -------------------------------- //

	/**
	 * Returns the long value of the field with the specified index.
	 *
	 * @return the value which may be null
	 */
	public Long getLong(int columnIndex) {

		return DbCasting.getLong(get(columnIndex));
	}

	/**
	 * Returns the long value of the field with the specified name.
	 *
	 * @return the value which may be null
	 */
	public Long getLong(String columnName) {

		return DbCasting.getLong(get(columnName));
	}

	// -------------------------------- GET LONG ZERO FOR NULL -------------------------------- //

	/**
	 * Returns the long value of the field with the specified index.
	 *
	 * @return the value or 0 if the database field is null
	 */
	public long getLong_ZeroForNull(int columnIndex) {

		Long result = getLong(columnIndex);
		return result != null? result : 0;
	}

	/**
	 * Returns the long value of the field with the specified name.
	 *
	 * @return the value or 0 if the database field is null
	 */
	public long getLong_ZeroForNull(String columnName) {

		Long result = getLong(columnName);
		return result != null? result : 0;
	}

	// -------------------------------- GET BIG DECIMAL -------------------------------- //

	public BigDecimal getBigDecimal(int columnIndex) {

		return DbCasting.getBigDecimal(get(columnIndex));
	}

	public BigDecimal getBigDecimal(String columnName) {

		return DbCasting.getBigDecimal(get(columnName));
	}

	// -------------------------------- GET DOUBLE -------------------------------- //

	public Double getDouble(int columnIndex) {

		return DbCasting.getDouble(get(columnIndex));
	}

	public Double getDouble(String columnName) {

		return DbCasting.getDouble(get(columnName));
	}

	// -------------------------------- GET FLOAT -------------------------------- //

	public Float getFloat(int columnIndex) {

		return DbCasting.getFloat(get(columnIndex));
	}

	public Float getFloat(String columnName) {

		return DbCasting.getFloat(get(columnName));
	}

	// -------------------------------- GET STRING -------------------------------- //

	public String getString(int columnIndex) {

		try {
			return resultSet.getString(columnIndex);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public String getString(String columnName) {

		try {
			return resultSet.getString(columnName);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	// -------------------------------- GET DATE/TIMESTAMP -------------------------------- //

	public Date getDate(int columnIndex) {

		try {
			return resultSet.getDate(columnIndex);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public Date getDate(String columnName) {

		try {
			return resultSet.getDate(columnName);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public Timestamp getTimestamp(int columnIndex) {

		try {
			return resultSet.getTimestamp(columnIndex);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public Timestamp getTimestamp(String columnName) {

		try {
			return resultSet.getTimestamp(columnName);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	// -------------------------------- GET DAY/DAYTIME/TIME -------------------------------- //

	public Day getDay(int columnIndex) {

		return DbCasting.getDay(getDate(columnIndex));
	}

	public Day getDay(String columnName) {

		return DbCasting.getDay(getDate(columnName));
	}

	public DayTime getDayTime(int columnIndex) {

		return DbCasting.getDayTime(getTimestamp(columnIndex));
	}

	public DayTime getDayTime(String columnName) {

		return DbCasting.getDayTime(getTimestamp(columnName));
	}

	public Time getTime(int columnIndex) {

		return DbCasting.getTime(get(columnIndex));
	}

	public Time getTime(String columnName) {

		return DbCasting.getTime(get(columnName));
	}

	// -------------------------------- GET BYTES -------------------------------- //

	public byte[] getBytes(String columnName) {

		try {
			return resultSet.getBytes(columnName);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public byte[] getBytes(int columnIndex) {

		try {
			return resultSet.getBytes(columnIndex);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	// -------------------------------- GET BINARY STREAM ---------------------------- //

	public InputStream getBinaryStream(String columnName) {

		try {
			return resultSet.getBinaryStream(columnName);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public InputStream getBinaryStream(int columnIndex) {

		try {
			return resultSet.getBinaryStream(columnIndex);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	// -------------------------------- GET ENUM -------------------------------- //

	public <T extends Enum<T>> T getEnum(Class<T> enumType, String columnName) {

		return getEnum_Auxiliary(enumType, getString(columnName));
	}

	public <T extends Enum<T>> T getEnum(Class<T> enumType, int columnIndex) {

		return getEnum_Auxiliary(enumType, getString(columnIndex));
	}

	private static <T extends Enum<T>> T getEnum_Auxiliary(Class<T> enumType, String value) {

		if (value != null) {
			try {
				Method getByNameMethod = enumType.getDeclaredMethod("getByName", String.class);
				return CastUtils.cast(getByNameMethod.invoke(null, value));
			} catch (NoSuchMethodException exception) {
				DevNull.swallow(exception);
				return Enum.valueOf(enumType, value);
			} catch (InvocationTargetException | IllegalAccessException exception) {
				throw new RuntimeException(exception);
			}
		} else {
			return null;
		}
	}

	// -------------------------------- GET SET -------------------------------- //

	public <T extends Enum<T>> TreeSet<T> getSet(Class<T> enumType, String columnName) {

		return getSet_Auxiliary(enumType, getString(columnName));
	}

	public <T extends Enum<T>> TreeSet<T> getSet(Class<T> enumType, int columnIndex) {

		return getSet_Auxiliary(enumType, getString(columnIndex));
	}

	private static <T extends Enum<T>> TreeSet<T> getSet_Auxiliary(Class<T> enumType, String value) {

		if (value != null) {
			TreeSet<T> set = new TreeSet<>();
			if (value.length() > 0) {
				for (String valuePart: value.split(",")) {
					set.add(getEnum_Auxiliary(enumType, valuePart));
				}
			}
			return set;
		} else {
			return null;
		}
	}

	// -------------------------------- GET SET -------------------------------- //

	public <T extends Enum<T>> EnumSet<T> getEnumSet(Class<T> enumType, String columnName) {

		return getEnumSet_Auxiliary(enumType, getString(columnName));
	}

	public <T extends Enum<T>> EnumSet<T> getEnumSet(Class<T> enumType, int columnIndex) {

		return getEnumSet_Auxiliary(enumType, getString(columnIndex));
	}

	private static <T extends Enum<T>> EnumSet<T> getEnumSet_Auxiliary(Class<T> enumType, String value) {

		if (value != null) {
			EnumSet<T> set = EnumSet.noneOf(enumType);
			if (value.length() > 0) {
				for (String valuePart: value.split(",")) {
					set.add(getEnum_Auxiliary(enumType, valuePart));
				}
			}
			return set;
		} else {
			return null;
		}
	}

	// -------------------------------- NAVIGATION -------------------------------- //

	public boolean first() {

		try {
			return resultSet.first();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public boolean isFirst() {

		try {
			return resultSet.isFirst();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public boolean last() {

		try {
			return resultSet.last();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public boolean isLast() {

		try {
			return resultSet.isLast();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public void beforeFirst() {

		try {
			resultSet.beforeFirst();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public boolean isBeforeFirst() {

		try {
			return resultSet.isBeforeFirst();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public boolean next() {

		try {
			return resultSet.next();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	public DbResultSet firstAndReturn() {

		try {
			resultSet.first();
			return this;
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	// -------------------------------- META DATA -------------------------------- //

	/**
	 * Returns the meta data of this result set.
	 *
	 * @return meta data object
	 */
	public ResultSetMetaData getMetaData() {

		try {
			return resultSet.getMetaData();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	/**
	 * Returns the number of columns of this result set.
	 *
	 * @return the column count
	 */
	public int getColumnCount() {

		try {
			return resultSet.getMetaData().getColumnCount();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	/**
	 * Returns the name of the column with the specified index.
	 *
	 * @param index
	 *            the index of the column starting from 1
	 * @return the name of the column
	 */
	public String getColumnName(int index) {

		try {
			return resultSet.getMetaData().getColumnName(index);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	@Override
	public void close() {

		try {
			resultSet.close();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	/**
	 * Returns is DBResultSet empty
	 *
	 * @return boolean: is DBResultSet empty
	 */
	public boolean isEmpty() {

		try {
			int current = resultSet.getRow();
			if (resultSet.next()) {
				if (current == 0) {
					resultSet.beforeFirst();
				} else {
					resultSet.absolute(current);
				}
				return false;
			} else {
				return true;
			}
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	/**
	 * Returns size of DBresultSet (Number of rows)
	 *
	 * @return int: size of DBresultSet (Number of rows)
	 */
	public int size() {

		try {
			int current = resultSet.getRow();
			resultSet.last();
			int size = resultSet.getRow();
			if (current == 0) {
				resultSet.beforeFirst();
			} else {
				resultSet.absolute(current);
			}
			return size;
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	/**
	 * returns the number of rows
	 *
	 * @return the number of rows
	 */
	public int getRowCount() {

		return size();
	}

	public ResultSet getResultSet() {

		return resultSet;
	}
}
