package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbIntegerField<R> extends AbstractDbPrimitiveField<R, Integer, DbIntegerField<R>> implements IDbIntegerField<R> {

	public static final int DEFAULT_BITS = 32;
	private int bits;
	private boolean unsigned;

	public DbIntegerField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, Integer> getter, BiConsumer<? super R, Integer> setter) {

		super(builder, SqlFieldType.INTEGER, name, getter, setter);

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

	public DbIntegerField<R> setBits(int bits) {

		this.bits = bits;
		return this;
	}

	public DbIntegerField<R> setUnsigned() {

		this.unsigned = true;
		return this;
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbIntegerField<R> getThis() {

		return this;
	}
}
