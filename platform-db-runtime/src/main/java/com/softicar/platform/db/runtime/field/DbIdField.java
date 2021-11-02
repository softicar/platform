package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.container.iterable.MappingIterable;
import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbIdField<R extends IDbObject<R>> extends AbstractDbPrimitiveField<R, Integer, DbIdField<R>> implements IDbIdField<R> {

	private static final SqlFieldType DEFAULT_FIELD_TYPE = SqlFieldType.INTEGER;
	private static final int DEFAULT_BITS_FOR_INTEGER = 32;
	private SqlFieldType physicalFieldType;
	private int bits;
	private boolean unsigned;

	public DbIdField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, Integer> getter, BiConsumer<? super R, Integer> setter) {

		super(builder, DEFAULT_FIELD_TYPE, name, getter, setter);

		this.physicalFieldType = DEFAULT_FIELD_TYPE;
		this.bits = DEFAULT_BITS_FOR_INTEGER;
		this.unsigned = false;
	}

	@Override
	public SqlFieldType getPhysicalFieldType() {

		return physicalFieldType;
	}

	@Override
	public int getBits() {

		return bits;
	}

	@Override
	public boolean isUnsigned() {

		return unsigned;
	}

	// ------------------------------ expressions ------------------------------ //

	@Override
	public ISqlBooleanExpression<R> isIn(Collection<R> rows) {

		return isIn(new MappingIterable<>(rows, IDbObject::getId));
	}

	@Override
	public ISqlBooleanExpression<R> isNotIn(Collection<R> rows) {

		return isNotIn(new MappingIterable<>(rows, IDbObject::getId));
	}

	/**
	 * Creates a boolean expression that compares this field and the ID of the
	 * given persistent table row for equality.
	 * <p>
	 * The handling of <i>null</i> values corresponds to the handling of
	 * <i>null</i> values in {@link ISqlForeignRowField}:
	 * <ul>
	 * <li>If the given table row is <i>null</i>, an {@link #isNull()}
	 * comparison is returned.</li>
	 * <li>If the ID of the given table row is <i>null</i>, an exception is
	 * thrown.</li>
	 * </ul>
	 *
	 * @param row
	 *            the persistent table row to compare to (may be null)
	 * @return boolean expression
	 */
	@Override
	public ISqlBooleanExpression<R> isEqual(R row) {

		return row != null? isEqual(getIdOrThrow(row)) : isNull();
	}

	/**
	 * Creates a boolean expression that compares this field and the ID of the
	 * given persistent table row for inequality.
	 * <p>
	 * The handling of <i>null</i> values corresponds to the handling of
	 * <i>null</i> values in {@link ISqlForeignRowField}:
	 * <ul>
	 * <li>If the given table row is <i>null</i>, an {@link #isNotNull()}
	 * comparison is returned.</li>
	 * <li>If the ID of the given table row is <i>null</i>, an exception is
	 * thrown.</li>
	 * </ul>
	 *
	 * @param row
	 *            the persistent table row to compare to (may be null)
	 * @return boolean expression
	 */
	@Override
	public ISqlBooleanExpression<R> isNotEqual(R row) {

		return row != null? isNotEqual(getIdOrThrow(row)) : isNotNull();
	}

	// ------------------------------ setters ------------------------------ //

	public DbIdField<R> setPhysicalFieldType(SqlFieldType physicalFieldType) {

		this.physicalFieldType = physicalFieldType;
		return this;
	}

	public DbIdField<R> setBits(int bits) {

		this.bits = bits;
		return this;
	}

	public DbIdField<R> setUnsigned() {

		this.unsigned = true;
		return this;
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbIdField<R> getThis() {

		return this;
	}

	// ------------------------------ private ------------------------------ //

	private Integer getIdOrThrow(R row) {

		Integer id = row.getId();
		if (id != null) {
			return id;
		} else {
			throw new IllegalArgumentException("Using non-persistent table row in comparison.");
		}
	}
}
