package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbLongField<R> extends AbstractDbPrimitiveField<R, Long, DbLongField<R>> implements IDbLongField<R> {

	public static final int DEFAULT_BITS = 64;
	private int bits;
	private boolean unsigned;

	public DbLongField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, Long> getter, BiConsumer<? super R, Long> setter) {

		super(builder, SqlFieldType.LONG, name, getter, setter);

		this.bits = DEFAULT_BITS;
		this.unsigned = false;
	}

	@Override
	public int getBits() {

		return bits;
	}

	@Override
	public boolean isUnsigned() {

		return unsigned;
	}

	// ------------------------------ setters ------------------------------ //

	public DbLongField<R> setBits(int bits) {

		this.bits = bits;
		return this;
	}

	public DbLongField<R> setUnsigned() {

		this.unsigned = true;
		return this;
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbLongField<R> getThis() {

		return this;
	}
}
