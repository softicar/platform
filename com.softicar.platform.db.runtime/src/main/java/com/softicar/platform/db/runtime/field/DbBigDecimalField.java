package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbBigDecimalField<R> extends AbstractDbPrimitiveField<R, BigDecimal, DbBigDecimalField<R>> implements IDbBigDecimalField<R> {

	private int precision;
	private int scale;

	public DbBigDecimalField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, BigDecimal> getter, BiConsumer<? super R, BigDecimal> setter) {

		super(builder, SqlFieldType.BIG_DECIMAL, name, getter, setter);

		this.precision = 20;
		this.scale = 4;
	}

	@Override
	public int getPrecision() {

		return precision;
	}

	@Override
	public int getScale() {

		return scale;
	}

	// ------------------------------ setters ------------------------------ //

	public DbBigDecimalField<R> setSize(int precision, int scale) {

		this.precision = precision;
		this.scale = scale;
		return this;
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbBigDecimalField<R> getThis() {

		return this;
	}
}
